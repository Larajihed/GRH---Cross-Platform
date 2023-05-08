<?php

namespace App\Controller;
use App\Entity\SoldeConge;
use App\Entity\Conge;
use App\Form\CongeType;
use App\Entity\User;
use Symfony\Component\Security\Core\Security;
use App\Repository\CongeRepository;
use App\Repository\SoldeCongeRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\VarDumper\Cloner\ClonerInterface;
use Symfony\Component\String\Slugger\SluggerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Knp\Component\Pager\Paginator;
use Symfony\Component\HttpFoundation\Session\Session;
use App\Repository\UserRepository;


#[Route('/conge_rh')]

class CongeRhController extends AbstractController
{
    private $paginator;



    public function __construct(PaginatorInterface $paginator)
    {
        $this->paginator = $paginator;
    }

    #[Route('/', name: 'app_conge_rh')]
    public function index(CongeRepository $congeRepository,Request $request, UserRepository $us, Session $session): Response
    {   $currentUser = $us->findOneBy(['id' => $session->get('id')]);
        if ($this->denyAccessUnlessGranted('ROLE_RESPONSABLE')){
             return $this->redirectToRoute('/login');
 
        };
        
        // $conges = $congeRepository->findBy(['etat' => 0]);
        $em = $this->getDoctrine()->getManager();
        $repository = $em->getRepository(Conge::class);
    
        $query = $repository->createQueryBuilder('c')
            ->where('c.etat = :etat')
            ->setParameter('etat', 0)
            ->orderBy('c.id', 'DESC')
            ->getQuery();
    
        $conges = $query->getResult();
        $page = $request->query->getInt('page', 1);
        $conges = $this->paginator->paginate($conges, $page, 8);
        
        return $this->render('conge_rh/index.html.twig', [
            'conges' => $conges,
        ]);
    }
    

#[Route('/accept/{id}', name: 'app_conge_accepter',methods: ['GET', 'POST'])]
public function accepter(Request $request, Conge $conge, CongeRepository $congeRepository,SoldeCongeRepository $soldeCongeRepository): Response

{
    $id_user= $conge ->getIdUser();
    $solde = $soldeCongeRepository->findOneBy(['id_user' => $id_user] );
   
            $x = $solde->getSolde();
            $interval = $conge->getDebut()->diff($conge->getFin());
            $days = $interval->days+1;

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
            
            // Calculez le nombre de jours ouvrables en soustrayant les week-ends du total de jours
            $workingDays = $days+1 - $weekends;





            
            
    $new_solde = $x-$workingDays;
   
    $solde->setSolde($new_solde);
   

    $soldeCongeRepository->save($solde, true);
    $conge->setEtat("1");
    $congeRepository->save($conge, true);


    return $this->redirectToRoute('app_conge_rh', [], Response::HTTP_SEE_OTHER);

}

#[Route('/refuser/{id}', name: 'app_conge_refuser',methods: ['GET', 'POST'])]
public function refuser(Request $request, Conge $conge, CongeRepository $congeRepository): Response

{
   

    $conge->setEtat("2");
    $congeRepository->save($conge, true);


    return $this->redirectToRoute('app_conge_rh', [], Response::HTTP_SEE_OTHER);

}


#[Route('/searchquery', name: 'search_conge')]
    public function findConge(Request $request): Response
    {
        $name = $request->request->get('searchTerm');

        if (!$name) {
            $conges = $this->getDoctrine()->getRepository(Conge::class)->findAll();
        } else {
            $conges = $this->getDoctrine()->getRepository(Conge::class)->findConge($name);
        }

        $tableHtml = $this->renderView('conge_rh/_table.html.twig', [
            'conges' => $conges,
            'searchTerm' => $name
        ]);

        return new Response($tableHtml);

    }

    #[Route('/search', name: 'search_conge_page')]
    public function findConges(Request $request) :Response
    {
        $name = $request->request->get('searchTerm');

        if (!$name) {
            $conges = $this->getDoctrine()->getRepository(Conge::class)->findAll();
        } else {
            $conges = $this->getDoctrine()->getRepository(Conge::class)->findConge($name);
        }


        return $this->render('conge_rh/search.html.twig', [
            'conges' => $conges,
            'searchTerm' => $name
        ]);
    }


#[Route('/historique', name: 'app_conge_historique', methods: ['GET'])]
public function historique(CongeRepository $congeRepository,Request $request): Response
{   
    $conges = $congeRepository->findAll();
    $page = $request->query->getInt('page', 1);
    $conges = $this->paginator->paginate($conges, $page, 12);
    
    return $this->render('conge_rh/historique.html.twig', [
        'conges' => $conges
    ]);
}


#[Route('/{id}', name: 'app_conge_rh_show', methods: ['GET'])]
    public function show(Conge $conge): Response
    {
        return $this->render('conge_rh/show.html.twig', [
            'conge' => $conge,
        ]);
    }


}