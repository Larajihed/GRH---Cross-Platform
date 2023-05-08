<?php

namespace App\Controller;

use App\Entity\SoldeConge;
use App\Form\SoldeCongeType;
use App\Form\EditsoldecongeType;
use App\Repository\SoldeCongeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use App\Entity\User;

use Symfony\Component\Routing\Annotation\Route;

#[Route('/solde/conge'), IsGranted('ROLE_USER')]
class SoldeCongeController extends AbstractController
{
    #[Route('/', name: 'app_solde_conge_index', methods: ['GET'])]
    public function index(SoldeCongeRepository $soldeCongeRepository): Response
    {
        return $this->render('solde_conge/index.html.twig', [
            'solde_conges' => $soldeCongeRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_solde_conge_new', methods: ['GET', 'POST'])]
    public function new(Request $request, SoldeCongeRepository $soldeCongeRepository): Response
    {
        $soldeConge = new SoldeConge();
        $form = $this->createForm(SoldeCongeType::class, $soldeConge);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $soldeCongeRepository->save($soldeConge, true);

            return $this->redirectToRoute('app_solde_conge_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('solde_conge/new.html.twig', [
            'solde_conge' => $soldeConge,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_solde_conge_show', methods: ['GET'])]
    public function show(SoldeConge $soldeConge): Response
    {
        return $this->render('solde_conge/show.html.twig', [
            'solde_conge' => $soldeConge,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_solde_conge_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, SoldeConge $soldeConge, SoldeCongeRepository $soldeCongeRepository): Response
    {
        $user = $soldeConge->getidUser();
        $form = $this->createForm(EditsoldecongeType::class, $soldeConge);
        $form->handleRequest($request);
    
        if ($form->isSubmitted()) {

            // Utiliser la valeur existante de l'utilisateur
            $soldeConge->setIdUser($user);

            $soldeCongeRepository->save($soldeConge, true);
            
            
            return $this->redirectToRoute('app_solde_conge_index', [], Response::HTTP_SEE_OTHER);
        }
        

        return $this->renderForm('solde_conge/edit.html.twig', [
            'solde_conge' => $soldeConge,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_solde_conge_delete', methods: ['POST'])]
    public function delete(Request $request, SoldeConge $soldeConge, SoldeCongeRepository $soldeCongeRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$soldeConge->getId(), $request->request->get('_token'))) {
            $soldeCongeRepository->remove($soldeConge, true);
        }

        return $this->redirectToRoute('app_solde_conge_index', [], Response::HTTP_SEE_OTHER);
    }
}
