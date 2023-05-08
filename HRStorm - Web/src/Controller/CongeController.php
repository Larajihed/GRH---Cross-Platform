<?php

namespace App\Controller;

use App\Entity\Conge;
use Symfony\Component\HttpFoundation\JsonResponse;
use App\Form\CongeType;
use App\Entity\User;
use App\Entity\SoldeConge;
use Symfony\Component\Form\FormError;

use Symfony\Component\Security\Core\Security;
use App\Repository\CongeRepository;
use App\Repository\UserRepository;
use App\Repository\SoldeCongeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\VarDumper\Cloner\ClonerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\String\Slugger\SluggerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Knp\Component\Pager\Paginator;
use Symfony\Component\Notifier\Notification\Notification;
use Symfony\Component\Notifier\NotifierInterface;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Component\HttpFoundation\Session\Session;


#[Route('/conge')]

class CongeController extends AbstractController
{

    private $paginator;



    public function __construct(PaginatorInterface $paginator)
    {
        $this->paginator = $paginator;
    }



    #[Route('/', name: 'app_conge_index', methods: ['GET'])]
    public function index(CongeRepository $congeRepository,FlashyNotifier $flashy,Security $security,SoldeCongeRepository $soldeCongeRepository,Request $request,Session $session,UserRepository $us): Response
    {   $currentUser = $us->findOneBy(['id' => $session->get('id')]);
        if ($this->denyAccessUnlessGranted('ROLE_RESPONSABLE')){
             return $this->redirectToRoute('/login');
 
        };
        $user = $security->getUser();
        $solde = $soldeCongeRepository->findOneBy(['id_user' => $user] );
        $x = $solde->getSolde();
        $em = $this->getDoctrine()->getManager();
        $repository = $em->getRepository(Conge::class);
    
        $user = $this->getUser();
        $query = $repository->createQueryBuilder('c')
            ->where('c.id_user = :user')
            ->setParameter('user', $user)
            ->orderBy('c.id', 'DESC')
            ->getQuery();
    
        $conges = $query->getResult();
        $page = $request->query->getInt('page', 1);
        $conges = $this->paginator->paginate($conges, $page, 5);
        
        $data = $congeRepository->countByEtat();

          
     


        return $this->render('conge/index.html.twig', [
            'conges' => $conges,
             'solde' =>$x,
            // 'conges' => $congeRepository->findAll(),
            'data' => $data,
        ]);
    }

    #[Route('/new', name: 'app_conge_new', methods: ['GET', 'POST'])]
    public function new(Request $request, CongeRepository $congeRepository,FlashyNotifier $flashy,Security $security,SluggerInterface $slugger,SoldeCongeRepository $soldeCongeRepository): Response
    {
        $conge = new Conge();
        $form = $this->createForm(CongeType::class, $conge);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            
            $user = $security->getUser();
            $solde = $soldeCongeRepository->findOneBy(['id_user' => $user] );
            $x = $solde->getSolde();
           
            // $userId = $user->getId();   
            
            $interval = $conge->getDebut()->diff($conge->getFin());
            $days = $interval->days;
            $weekends = 0;
            $current = clone $conge->getDebut();
            while ($current <= $conge->getFin()) {
                $dayOfWeek = $current->format('N'); // N pour numéro du jour de la semaine (1 pour lundi, 7 pour dimanche)
                if ($dayOfWeek >= 6) { // Si le jour est un samedi (6) ou un dimanche (7)
                    $weekends++;
                }
                $current->modify('+1 day'); // Passez au jour suivant

                
            }
            if (($weekends == 2 && $days + 1 == 2) || ($weekends == 1 && $days + 1 == 1)) {
                $this->addFlash('warning', 'Le Weekend est par défaut votre congé');
                $response = "";
                return $this->renderForm('conge/new.html.twig', [
                    'response' => $response,
                    'form' => $form,
                    'solde'=> $x,
                    'flashy'=>$flashy
                    
                ]);

            }
            
            // Calculez le nombre de jours ouvrables en soustrayant les week-ends du total de jours
            $workingDays = $days - $weekends;






            if($workingDays>$x-1) {
                $response = "solde insuffisant";
                return $this->renderForm('conge/new.html.twig', [
                    'response' => $response,
                    'form' => $form,
                    'solde'=> $x,
                    'interval'=>$workingDays+1,
                    
                ]);
            }
            $new_solde = $x-$days;

            
            $conge->setIdUser($user);
            $conge->setEtat("0");
            $image = $form->get('image')->getData();

            // this condition is needed because the 'brochure' field is not required
            // so the PDF file must be processed only when a file is uploaded
            if ($image) {
                $originalFilename = pathinfo($image->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = time().'-'.uniqid().'.'.$image->guessExtension();

                // Move the file to the directory where brochures are stored
                try {
                    $image->move(
                        $this->getParameter('conges_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // updates the 'brochureFilename' property to store the PDF file name
                // instead of its contents
                $conge->setImage($newFilename);
            }
            

            $congeRepository->save($conge, true);
            $this->addFlash('success', 'Congé demander avec succés');

            return $this->redirectToRoute('app_conge_index', [], Response::HTTP_SEE_OTHER);
        }

        $user = $security->getUser();
        $solde = $soldeCongeRepository->findOneBy(['id_user' => $user] );
        $x = $solde->getSolde();    
            $days='';
        $response='';
        return $this->renderForm('conge/new.html.twig', [
            'conge' => $conge,
            'form' => $form,
            'solde'=> $x,
            'response'=> $response,
            'interval'=>$days,
            'flashy' => $flashy,

            
        ]);
    }

    #[Route('/{id}', name: 'app_conge_show', methods: ['GET'])]
    public function show(Conge $conge): Response
    {
        return $this->render('conge/show.html.twig', [
            'conge' => $conge,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_conge_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request,SluggerInterface $slugger, Conge $conge, CongeRepository $congeRepository, Security $security,SoldeCongeRepository $soldeCongeRepository): Response
    {
        $user = $security->getUser();

        $solde = $soldeCongeRepository->findOneBy(['id_user' => $user] );
        $x = $solde->getSolde();
        $form = $this->createForm(CongeType::class, $conge);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $image = $form->get('image')->getData();

            if ($image) {
                $originalFilename = pathinfo($image->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = time().'-'.uniqid().'.'.$image->guessExtension();

                // Move the file to the directory where brochures are stored
                try {
                    $image->move(
                        $this->getParameter('conges_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // updates the 'brochureFilename' property to store the PDF file name
                // instead of its contents
                $conge->setImage($newFilename);
            }
            $congeRepository->save($conge, true);

            return $this->redirectToRoute('app_conge_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('conge/edit.html.twig', [
            'conge' => $conge,
            'form' => $form,
            'solde' => $x,

        ]);
    }

    #[Route('/{id}', name: 'app_conge_delete', methods: ['POST'])]
    public function delete(Request $request, Conge $conge, CongeRepository $congeRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$conge->getId(), $request->request->get('_token'))) {
            $congeRepository->remove($conge, true);
        }

        return $this->redirectToRoute('app_conge_index', [], Response::HTTP_SEE_OTHER);
    }
}