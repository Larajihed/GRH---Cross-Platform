<?php

namespace App\Controller;

use App\Entity\Competence;
use App\Form\CompetenceType;
use App\Repository\CompetenceRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\Persistence\ManagerRegistry;

use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\HttpFoundation\Session\Session;
use App\Repository\UserRepository;

#[Route('/hremployee/competence')]
class CompetenceController extends AbstractController
{













    ///////////
    /////////// SYMFONY Controllers
    /////////////
   
    #[Route('/', name: 'app_competence_index', methods: ['GET'])]
    public function index(CompetenceRepository $competenceRepository,Session $session,UserRepository $us): Response
    {   $currentUser = $us->findOneBy(['id' => $session->get('id')]);
       if ($this->denyAccessUnlessGranted('ROLE_RESPONSABLE')){
            return $this->redirectToRoute('/login');

       };
        return $this->render('hr_employee/competence/index.html.twig', [
            'competences' => $competenceRepository->findAll(),
        ]);
    }
    #[Route('/newcompetence', name: 'mobileapp_competence_new', methods: ['GET','POST'])]
    public function ajouterCompetence(Request $request, CompetenceRepository $competenceRepository)
    {
        $competence = new Competence();
        $nom=$request->query->get("Nom");
        $description=$request->query->get("description");
        $em = $this->getDoctrine()->getManager();
        $date = new \DateTime();
        $competence -> setNom($nom);
        $competence -> setDescription($description);
        $em->persist($competence);
        $em-> flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer -> normalize($competence);
        return new JsonResponse($formatted);
    }
     
    #[Route('/get', name: 'mobileapp_poste_index', methods: ['GET'])]
    public function getAll(CompetenceRepository $competenceRepository): JsonResponse
    {
        $competences = $competenceRepository->findAll();
        $data = [];
    
        foreach ($competences as $competence) {
            $data[] = [
                'Id' => $competence->getId(),
                'Nom' => $competence->getNom(),
                'Description' => $competence->getDescription(),
                // Add other properties as needed
            ];
        }
    
        return new JsonResponse($data);
    }
    #[Route('/new', name: 'app_competence_new', methods: ['GET', 'POST'])]
    public function new(Request $request, CompetenceRepository $competenceRepository): Response
    {
        $competence = new Competence();
        $form = $this->createForm(CompetenceType::class, $competence);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $competenceRepository->save($competence, true);

            return $this->redirectToRoute('app_competence_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('hr_employee/competence/new.html.twig', [
            'competence' => $competence,
            'form' => $form,
        ]);
    }
    #[Route('/{id}', name: 'app_competence_show', methods: ['GET'])]
    public function show(Competence $competence): Response
    {
        return $this->render('hr_employee/competence/show.html.twig', [
            'competence' => $competence,
        ]);
    }

      
    #[Route('/{id}/edit', name: 'app_competence_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Competence $competence, CompetenceRepository $competenceRepository): Response
    {
        $form = $this->createForm(CompetenceType::class, $competence);

        if ($form->isSubmitted() && $form->isValid()) {
            $competenceRepository->save($competence, true);

            return $this->redirectToRoute('app_competence_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('hr_employee/competence/edit.html.twig', [
            'competence' => $competence,
            'form' => $form,
        ]);
    }


    #[Route('/{id}', name: 'app_competence_delete', methods: ['POST'])]
    public function delete(Request $request, Competence $competence, CompetenceRepository $competenceRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$competence->getId(), $request->request->get('_token'))) {
            $competenceRepository->remove($competence, true);
        }
        
        return $this->redirectToRoute('app_competence_index', [], Response::HTTP_SEE_OTHER);
    }












 ///////////
    /////////// Mobile Controllers
    /////////////

   
    
 

    #[Route('/UpdateCompetenceJSON/{id}', name: 'UpdateBudgetJSON')]
    public function UpdateBudgetJSON($id, Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $Budget = $this->getDoctrine()->getRepository(Competence::class)->find($id);
        $Budget->setNom($request->get('Nom'));
        $Budget->setDescription($request->get('Description'));
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer -> normalize($Budget);
        return new JsonResponse($formatted);
    }
    
    
    
    #[Route('/delete/{id}', name: 'mobileapp_competence_delete')]
    public function deleteCompetence(ManagerRegistry $doctrine, Request $request, Competence $competence, CompetenceRepository $competenceRepository): JsonResponse
    { 
        $id = $request->get("id");
        $em = $this->getDoctrine()->getManager();
        $c = $doctrine->getRepository(Competence::class)->find($id);
        if($c != null){
            $em->remove($c);
            $em->flush();
            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize("Candidat supprimer avec succées");
            
            return new JsonResponse($formatted);
        }
        return new JsonResponse("id invalide");
    }
    
    
 
}
