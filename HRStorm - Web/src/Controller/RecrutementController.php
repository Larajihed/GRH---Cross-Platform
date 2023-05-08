<?php

namespace App\Controller;
use App\Entity\Candidat;
use Doctrine\ORM\EntityManagerInterface;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Notifier\Notification\Notification;
use Symfony\Component\Notifier\NotifierInterface;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Recrutement;
use App\Form\RecrutementType;
use App\Repository\RecrutementRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\PropertyInfo\Extractor\ReflectionExtractor;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Process\Process;



#[Route('/employee/recrutement')]
class RecrutementController extends AbstractController
{
    #[Route('/', name: 'app_recrutement_index', methods: ['GET'])]
    public function index(RecrutementRepository $recrutementRepository, Request $request, EntityManagerInterface $em, PaginatorInterface $paginator): Response
    {
        $min_resultat = $request->query->get('min_resultat');
        $max_resultat = $request->query->get('max_resultat');

        $queryBuilder = $em->getRepository(Recrutement::class)->createQueryBuilder('r');

        if ($min_resultat) {
            $queryBuilder->andWhere('r.nbrposte >= :min_nbrposte')
                ->setParameter('min_nbrposte', $min_resultat);
        }

        if ($max_resultat) {
            $queryBuilder->andWhere('r.nbrposte <= :max_nbrposte')
                ->setParameter('max_nbrposte', $max_resultat);
        }

        $recrutements = $queryBuilder->getQuery()->getResult();
        $tests = $paginator->paginate(
            $recrutements,
            $request->query->getInt('page', 1),
            5 // number of items per page
        );
        return $this->render('recrutement/index.html.twig', [
            'recrutements' => $tests,
        ]);
    }

    #[Route('/list', name: 'app_recrutements_index', methods: ['GET'])]
    public function showjobposts(RecrutementRepository $recrutementRepository): Response
    {
        return $this->render('recrutement/listrecrutements.html.twig', [
            'recrutements' => $recrutementRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_recrutement_new', methods: ['GET', 'POST'])]
    public function new(Request $request, RecrutementRepository $recrutementRepository,FlashyNotifier $flashy): Response
    {
        $recrutement = new Recrutement();
        $form = $this->createForm(RecrutementType::class, $recrutement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {


            $recrutementRepository->save($recrutement, true);
            $this->addFlash('success', 'Campagne ajouté avec succes');
            return $this->redirectToRoute('app_recrutement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('recrutement/new.html.twig', [
            'recrutement' => $recrutement,
            'form' => $form,
            'flashy' => $flashy,

        ]);
    }

    //////////////////mobile

    #[Route('/affiche_offre_mobile', name: 'affiche_offre_mobile')]
    public function indexmobile(ManagerRegistry $doctrine, Request $request): Response
    {
        $recrutment = $doctrine->getRepository(Recrutement::class)->findAll();

        $normalizer = new ObjectNormalizer(null, null, null, new ReflectionExtractor(), null, null, [
            ObjectNormalizer::CIRCULAR_REFERENCE_HANDLER => function ($object) {
                return $object->getId();
            },
            ObjectNormalizer::ATTRIBUTES => ['id', 'titre','description','nbrposte','salaire','type']
        ]);

        $serializer = new Serializer([$normalizer], [new JsonEncoder()]);

        $formatted = $serializer->normalize($recrutment);

        return new JsonResponse($formatted);
    }
    #[Route('/modifier_offre_mobile', name: 'modifier_offre_mobile')]
    public function modifmobile(ManagerRegistry $doctrine, Request $request): Response
    {
        $em = $doctrine->getManager();
        $recrutment = $doctrine->getRepository(Recrutement::class)->find($request->query->get("id"));

        $recrutment->setDescription($request->query->get("description"));
        $recrutment->setNbrposte($request->query->get("nbreposte"));
        $recrutment->setTitre($request->query->get("titre"));
        $recrutment->setSalaire($request->query->get("salaire"));
        $recrutment->setType($request->query->get("type"));
        $em->persist($recrutment);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);

        $formatted = $serializer->normalize("Campagne de recrutment modifier avec succées");

        return new JsonResponse($formatted);
    }
    #[Route('/supprimer_offre_mobile', name: 'supprimer_offre_mobile')]
    public function suppmobile(ManagerRegistry $doctrine, Request $request): Response
    {
        $id = $request->get("id");
        $em=$doctrine->getManager();
        $recrutment = $doctrine->getRepository(Recrutement::class)->find($id);
        if($recrutment != null){
            $em->remove($recrutment);
            $em->flush();
            $serializer = new Serializer([new ObjectNormalizer()]);

            $formatted = $serializer->normalize("Campagne de recrutment supprimer avec succées");

            return new JsonResponse($formatted);
        }
        return new JsonResponse("id invalide");
    }
    #[Route('/ajouter_offre_mobile', name: 'ajouter_offre_mobile')]
    public function ajoutmobile(ManagerRegistry $doctrine, Request $request): Response
    {
        $recrutment = new Recrutement();
        $em = $doctrine->getManager();

        $recrutment->setDescription($request->query->get("description"));
        $recrutment->setNbrposte($request->query->get("nbreposte"));
        $recrutment->setTitre($request->query->get("titre"));
        $recrutment->setSalaire($request->query->get("salaire"));
        $recrutment->setType($request->query->get("type"));
        $em->persist($recrutment);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);

        $formatted = $serializer->normalize($recrutment);

        return new JsonResponse($formatted);
    }

    #[Route('/candidt_mobile', name: 'candidat_mobile')]
    public function candidatmobile(ManagerRegistry $doctrine, Request $request): Response
    {
        $recrutment = $doctrine->getRepository(Candidat::class)->findAll();

        $normalizer = new ObjectNormalizer(null, null, null, new ReflectionExtractor(), null, null, [
            ObjectNormalizer::CIRCULAR_REFERENCE_HANDLER => function ($object) {
                return $object->getId();
            },
            ObjectNormalizer::ATTRIBUTES => ['id', 'nom','prenom','tel','email','lettremotivation','etat']
        ]);

        $serializer = new Serializer([$normalizer], [new JsonEncoder()]);

        $formatted = $serializer->normalize($recrutment);

        return new JsonResponse($formatted);
    }
    #[Route('/modifier_candidat_mobile', name: 'modifier_candidat_mobile')]
    public function modifcandidat(ManagerRegistry $doctrine, Request $request): Response
    {
        $em = $doctrine->getManager();
        $candidat = $doctrine->getRepository(Candidat::class)->find($request->query->get("id"));
         $candidat->setEtat(1);

        $em->persist($candidat);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);

        $formatted = $serializer->normalize("Candidat Accepter");

        return new JsonResponse($formatted);
    }
    #[Route('/refuser_candidat_mobile', name: 'refuser_candidat_mobile')]
    public function refucandidat(ManagerRegistry $doctrine, Request $request): Response
    {
        $em = $doctrine->getManager();
        $candidat = $doctrine->getRepository(Candidat::class)->find($request->query->get("id"));
        $candidat->setEtat(2);

        $em->persist($candidat);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);

        $formatted = $serializer->normalize("Candidat refuser");

        return new JsonResponse($formatted);
    }
    #[Route('/supprimer_candiat_mobile', name: 'supprimer_candidat_mobile')]
    public function suppcandidat(ManagerRegistry $doctrine, Request $request): Response
    {
        $id = $request->get("id");
        $em=$doctrine->getManager();
        $recrutment = $doctrine->getRepository(Candidat::class)->find($id);
        if($recrutment != null){
            $em->remove($recrutment);
            $em->flush();
            $serializer = new Serializer([new ObjectNormalizer()]);

            $formatted = $serializer->normalize("Candidat supprimer avec succées");

            return new JsonResponse($formatted);
        }
        return new JsonResponse("id invalide");
    }

















        #[Route('/{id}', name: 'app_recrutement_show', methods: ['GET'])]
        public function show(Recrutement $recrutement): Response
        {
            return $this->render('recrutement/show.html.twig', [
                'recrutement' => $recrutement,
            ]);
        }

        #[Route('/{id}/edit', name: 'app_recrutement_edit', methods: ['GET', 'POST'])]
        public function edit(Request $request, Recrutement $recrutement, RecrutementRepository $recrutementRepository): Response
        {
            $form = $this->createForm(RecrutementType::class, $recrutement);
            $form->handleRequest($request);

            if ($form->isSubmitted() && $form->isValid()) {
                $recrutementRepository->save($recrutement, true);

                return $this->redirectToRoute('app_recrutement_index', [], Response::HTTP_SEE_OTHER);
            }

            return $this->renderForm('recrutement/edit.html.twig', [
                'recrutement' => $recrutement,
                'form' => $form,
            ]);
        }

        #[Route('/{id}', name: 'app_recrutement_delete', methods: ['POST'])]
        public function delete(Request $request, Recrutement $recrutement, RecrutementRepository $recrutementRepository): Response
        {
            if ($this->isCsrfTokenValid('delete'.$recrutement->getId(), $request->request->get('_token'))) {
                $recrutementRepository->remove($recrutement, true);
            }

            return $this->redirectToRoute('app_recrutement_index', [], Response::HTTP_SEE_OTHER);
        }





}
