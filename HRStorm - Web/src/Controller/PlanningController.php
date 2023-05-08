<?php

namespace App\Controller;

use App\Entity\Planning;
use App\Form\PlanningType;
use App\Repository\PlanningRepository;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Notifier\Notification\Notification;
use Symfony\Component\Notifier\NotifierInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\AbstractNormalizer;


#[Route('/planning')]
class PlanningController extends AbstractController
{#[Route('/allplanning', name: 'json_allplanning')]
    public function getTournoi(NormalizerInterface $normalizer,  PlanningRepository $planning): Response
    {
        $plan=$planning->findAll();
        $planningNormalizer=$normalizer->normalize($plan,'json',['groups'=>"planning"]);
        $json=json_encode($planningNormalizer);
        return new Response($json);
    }

    #[Route('/JSON/getAll', name: 'app_plan_JSON', methods: ['GET'])]
public function index_JSON(SerializerInterface $serializer, PlanningRepository $planningRepository): JsonResponse
{
    $velos = $planningRepository->findAll();
    $json = $serializer->serialize($velos, 'json',[
        AbstractNormalizer::IGNORED_ATTRIBUTES => ['taches'],
    ]);

    return new JsonResponse($json, 200, [], true);
}

    #[Route('/addplanning', name: 'json_addplanning')]
    public function addTournoi(NormalizerInterface $normalizer, Request $req): Response
    {
        $em=$this->getDoctrine()->getManager();
        $planning=new Planning();

        $planning->setNom($req->get('nom'));
        $planning->setDescription($req->get('description'));        
        $planning->setDateDebut(new \DateTime());
        $planning->setDateFin(new \DateTime());
        $em->persist($planning);
        $em->flush();
        $planningNormalizer=$normalizer->normalize($planning,'json',['groups'=>"planning"]);
        $json=json_encode($planningNormalizer);
        return new Response($json);
    } 
    
    #[Route('/editplanning', name: 'json_editplanning')]
    public function editplann(NormalizerInterface $normalizer, Request $req,PlanningRepository $planningRepository): Response
    {
        $em=$this->getDoctrine()->getManager();
        $planning=$planningRepository->find($req->get('id'));

        $planning->setNom($req->get('nom'));
        $planning->setDescription($req->get('description'));        
        $planning->setDateDebut(new \DateTime());
        $planning->setDateFin(new \DateTime());
        $em->persist($planning);
        $em->flush();
        $planningNormalizer=$normalizer->normalize($planning,'json',['groups'=>"planning"]);
        $json=json_encode($planningNormalizer);
        return new Response($json);
    } 
    #[Route('/delplanning/{id}', name: 'json_delplan')]
    public function delMatches(NormalizerInterface $normalizer, Request $req, $id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $planning=$em->getRepository(Planning::class)->find($id);
        $em->remove($planning);
        $em->flush();
        $planningNormalizer=$normalizer->normalize($planning,'json',['groups'=>"planning"]);
        $json=json_encode($planningNormalizer);
        return new Response("Suppression avec success".$json);
    }
    #[Route('/modplanning/{id}', name: 'json_modplan')]
    public function modifyTournoi(NormalizerInterface $normalizer,$id, Request $req): Response
    {
        $em=$this->getDoctrine()->getManager();
        $planning=$em->getRepository(Planning::class)->find($id);
        $planning->setNom($req->get('nom'));        
        $em->flush();
        $planningNormalizer=$normalizer->normalize($planning,'json',['groups'=>"planning"]);
        $json=json_encode($planningNormalizer);
        return new Response("Modification avec success".$json);
    } 


    ////////////////////////////////////////
    #[Route('/', name: 'app_planning_index', methods: ['GET'])]
    public function index(PlanningRepository $planningRepository): Response
    {
        $plan=$planningRepository->findAll();
        $plannings=$planningRepository->findAll();

        foreach ($plan as $event)
        {
            $rdvs[]=[
                'title'=>$event->getNom(),
                'start'=>$event->getDateDebut()->format("Y-m-d"),
                'end'=>$event->getDateFin()->format("Y-m-d"),
                'backgroundColor'=> '#0ec51',
                'borderColor'=> 'green',
                'textColor' => 'black'
            ];
        }
        $data = json_encode($rdvs);
   //     return $this->render('planning/index.html.twig', compact('data', 'plannings'));

        return $this->render('planning/index.html.twig', compact('data', 'plannings'));
    }

//    #[Route('/priorite', name: 'app_planning_priorite', methods: ['GET'])]
//    public function priorite(PlanningRepository $repository): Response
//    {
//        $plannings = $repository->priorite();
//        $planning = $repository->findAll();
//
//        foreach ($planning as $event)
//        {
//            $rdvs[]=[
//                'title'=>$event->getNom(),
//                'start'=>$event->getDateDebut()->format("Y-m-d"),
//                'end'=>$event->getDateFin()->format("Y-m-d"),
//                'backgroundColor'=> '#0ec51',
//                'borderColor'=> 'green',
//                'textColor' => 'black'
//            ];
//        }
//        $data = json_encode($rdvs);
//        return $this->render('planning/index.html.twig',compact('plannings','data'));
//    }




    #[Route('/datedebutC', name: 'app_planning_datedebutC', methods: ['GET'])]
    function croissant(PlanningRepository $repository){
        $plannings = $repository->trie_croissant_datedeb();
        $planning = $repository->findAll();

        foreach ($planning as $event)
        {
            $rdvs[]=[
                'title'=>$event->getNom(),
                'start'=>$event->getDateDebut()->format("Y-m-d"),
                'end'=>$event->getDateFin()->format("Y-m-d"),
                'backgroundColor'=> '#0ec51',
                'borderColor'=> 'green',
                'textColor' => 'black'
            ];
        }
        $data = json_encode($rdvs);
        return $this->render('planning/index.html.twig',compact('plannings','data'));
    }

    #[Route('/datedebutD', name: 'app_planning_datedebutD', methods: ['GET'])]
    function decroissant(PlanningRepository $repository){
        $plannings= $repository->trie_decroissant_datedeb();
        $planning=$repository->findAll();

        foreach ($planning as $event)
        {
            $rdvs[]=[
                'title'=>$event->getNom(),
                'start'=>$event->getDateDebut()->format("Y-m-d"),
                'end'=>$event->getDateFin()->format("Y-m-d"),
                'backgroundColor'=> '#0ec51',
                'borderColor'=> 'green',
                'textColor' => 'black'
            ];
        }
        $data = json_encode($rdvs);
        return $this->render('planning/index.html.twig',compact('plannings','data'));
    }


    #[Route('/datefinC', name: 'app_planning_datefinC', methods: ['GET'])]
    function croissantF(PlanningRepository $repository){
        $plannings= $repository->trie_croissant_datefin();
        $planning=$repository->findAll();

        foreach ($planning as $event)
        {
            $rdvs[]=[
                'title'=>$event->getNom(),
                'start'=>$event->getDateDebut()->format("Y-m-d"),
                'end'=>$event->getDateFin()->format("Y-m-d"),
                'backgroundColor'=> '#0ec51',
                'borderColor'=> 'green',
                'textColor' => 'black'
            ];
        }
        $data = json_encode($rdvs);
        return $this->render('planning/index.html.twig',compact('plannings','data'));
    }

    #[Route('/datefinD', name: 'app_planning_datefinD', methods: ['GET'])]
    function decroissantF(PlanningRepository $repository){
        $plannings= $repository->trie_decroissant_datefin();
        $planning=$repository->findAll();

        foreach ($planning as $event)
        {
            $rdvs[]=[
                'title'=>$event->getNom(),
                'start'=>$event->getDateDebut()->format("Y-m-d"),
                'end'=>$event->getDateFin()->format("Y-m-d"),
                'backgroundColor'=> '#0ec51',
                'borderColor'=> 'green',
                'textColor' => 'black'
            ];
        }
        $data = json_encode($rdvs);
        return $this->render('planning/index.html.twig',compact('plannings','data'));
    }

    #[Route('/new', name: 'app_planning_new', methods: ['GET', 'POST'])]
    public function new(Request $request, PlanningRepository $planningRepository,FlashyNotifier $flashy): Response
    {
        $planning = new Planning();
        $form = $this->createForm(PlanningType::class, $planning);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $planningRepository->save($planning, true);

            $this->addFlash('success', 'This Planning added successfully');
            return $this->redirectToRoute('app_planning_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('planning/new.html.twig', [
            'planning' => $planning,
            'form' => $form,
            'flashy' => $flashy,
        ]);
    }

    #[Route('/{id}', name: 'app_planning_show', methods: ['GET'])]
    public function show(Planning $planning): Response
    {
        return $this->render('planning/show.html.twig', [
            'planning' => $planning,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_planning_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Planning $planning, PlanningRepository $planningRepository): Response
    {
        $form = $this->createForm(PlanningType::class, $planning);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $planningRepository->save($planning, true);

            return $this->redirectToRoute('app_planning_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('planning/edit.html.twig', [
            'planning' => $planning,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_planning_delete', methods: ['POST'])]
    public function delete(Request $request, Planning $planning, PlanningRepository $planningRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$planning->getId(), $request->request->get('_token'))) {
            $planningRepository->remove($planning, true);
            $this->addFlash('danger', 'This Planning removed successfully');
        }


        return $this->redirectToRoute('app_planning_index', [], Response::HTTP_SEE_OTHER);
    }

    //  --------------------------------------------------  AJOUT JSON

    #[Route('/planningJson/new', name: 'app_planning_ajout_json')]
    public function addPlanningJson(Request $request, EntityManagerInterface $entityManager): Response {
        $nom = $request->query->get('nom');
        $description = $request->query->get('description');
    
        $planning = new Planning();
        $planning->setDescription($description);
        $planning->setNom($nom);
        $entityManager->persist($planning);
        $entityManager->flush();
        try{

        return new JsonResponse("Planning is created", 200);
    }
    catch (\Exception $ex) {
     return new Response ( "exception".$ex->getMessage());   
    }
    }
}
