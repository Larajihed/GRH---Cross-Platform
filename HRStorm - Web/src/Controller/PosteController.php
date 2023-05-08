<?php

namespace App\Controller;

use App\Entity\Poste;
use App\Form\PosteType;
use App\Repository\PosteRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

#[Route('/hremployee/poste')]
class PosteController extends AbstractController
{
    #[Route('/', name: 'app_poste_index', methods: ['GET'])]
    public function index(PosteRepository $posteRepository): Response
    {
        return $this->render('/hr_employee/poste/index.html.twig', [
            'postes' => $posteRepository->findAll(),
        ]);
    }
    
    #[Route('/newposte', name: 'poste_new_mobile')]
    public function createPoste(Request $request): JsonResponse
    {
    
        $p = new Poste();
        $nom=$request->query->get("Nom");
        $missions=$request->query->get("Missions");
        $description=$request->query->get("Description");
        $salaireMax=$request->query->get("SALAIRE_MAX");
        $salaireMin=$request->query->get("SALAIRE_MIN");
        $p -> setNom($nom);
        $p -> setDescription($description);
        $p -> setMissions($missions);
        $p -> setSALAIREMAX($salaireMax);
        $p -> setSALAIREMIN($salaireMin);
    
        // Add competences to the poste
       
    
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($p);
        $entityManager->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer -> normalize($p);
        return new JsonResponse($formatted);

        return new JsonResponse($formatted);
    }
    #[Route('/new', name: 'app_poste_new', methods: ['GET', 'POST'])]
    public function new(Request $request, PosteRepository $posteRepository): Response
    {
        $poste = new Poste();
        $form = $this->createForm(PosteType::class, $poste);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $posteRepository->save($poste, true);

            return $this->redirectToRoute('app_poste_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('/hr_employee/poste/new.html.twig', [
            'poste' => $poste,
            'form' => $form,
        ]);
    }

    
    #[Route('/get', name: 'mobileapp_competence_index', methods: ['GET'])]
    public function getAll(PosteRepository $posteRepository): JsonResponse
    {
        $postes = $posteRepository->findAll();
        $data = [];
    
        foreach ($postes as $poste) {
            $competences = [];
            foreach ($poste->getCompetences() as $competence) {
                $competences[] = [
                    'Id' => $competence->getId(),
                    'Nom' => $competence->getNom(),
                   // 'Description' => $competence->getDescription(),
                    // Add other properties as needed
                ];
            }
            $data[] = [
                'Id' => $poste->getId(),
                'Nom' => $poste->getNom(),
                'Missions' => $poste->getMissions(),
                'Description' => $poste->getDescription(),
                'Competences' => $competences,
                'Max' => $poste->getSALAIREMAX(),
                'Min' => $poste->getSALAIREMIN(),
                // Add other properties as needed
            ];
        }
    
        return new JsonResponse($data);
    }


    #[Route('/{id}', name: 'app_poste_show', methods: ['GET'])]
    public function show(Poste $poste): Response
    {
        return $this->render('/hr_employee/poste/show.html.twig', [
            'poste' => $poste,
        ]);
    }

    
    #[Route('/{id}', name: 'app_poste_delete', methods: ['POST'])]
    public function delete(Request $request, Poste $poste, PosteRepository $posteRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$poste->getId(), $request->request->get('_token'))) {
            $posteRepository->remove($poste, true);
        }

        return $this->redirectToRoute('app_poste_index', [], Response::HTTP_SEE_OTHER);
    }




    


    #[Route('/{id}/edit', name: 'app_poste_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Poste $poste, PosteRepository $posteRepository): Response
    {
        $form = $this->createForm(PosteType::class, $poste);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $posteRepository->save($poste, true);

            return $this->redirectToRoute('app_poste_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('/hr_employee/poste/edit.html.twig', [
            'poste' => $poste,
            'form' => $form,
        ]);
    }
  

  

    

 ///////////
    /////////// Mobile Controllers
    /////////////




    #[Route('/delete/{id}', name: 'mobileapp_poste_delete')]
    public function deleteCompetence(ManagerRegistry $doctrine, Request $request, Poste $poste, PosteRepository $posteRepository): JsonResponse
    { 
        $id = $request->get("id");
        $em = $this->getDoctrine()->getManager();
        $c = $doctrine->getRepository(Poste::class)->find($id);
        if($c != null){
            $em->remove($c);
            $em->flush();
            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize("Poste supprimer avec succées");
            
            return new JsonResponse($formatted);
        }
        return new JsonResponse("id invalide");
    }
    
}
