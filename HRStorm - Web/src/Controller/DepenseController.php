<?php

namespace App\Controller;

use App\Entity\Depense;
use App\Entity\Budget;
use App\Form\DepenseType;
use App\Repository\BudgetRepository;
use App\Repository\DepenseRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;



#[Route('/depense')]
class DepenseController extends AbstractController
{
    #[Route('/', name: 'app_depense_index', methods: ['GET'])]
    public function index(DepenseRepository $depenseRepository): Response
    {
        return $this->render('depense/index.html.twig', [
            'depenses' => $depenseRepository->findAll(),
        ]);
    }

    #[Route('/searchquery', name: 'search_depense')]
    public function findDepense(Request $request): Response
    {
        $namee = $request->request->get('searchTerm');

        if (!$namee) {
            $depenses = $this->getDoctrine()->getRepository(Depense::class)->findAll();
        } else {
            $depenses = $this->getDoctrine()->getRepository(Depense::class)->findDepense($namee);
        }

        $tableHtml = $this->renderView('depense/_table.html.twig', [
            'depenses' => $depenses,
            'searchTerm' => $namee
        ]);

        return new Response($tableHtml);

    }

    #[Route('/search', name: 'search_depense_page')]
    public function findDepenses(Request $request) :Response
    {
        $namee = $request->request->get('searchTerm');

        if (!$namee) {
            $depenses = $this->getDoctrine()->getRepository(Depense::class)->findAll();
        } else {
            $depenses = $this->getDoctrine()->getRepository(Depense::class)->findDepense($namee);
        }


        return $this->render('depense/search.html.twig', [
            'depenses' => $depenses,
            'searchTerm' => $namee
        ]);
    }

    #[Route('/DateNow', name: 'DateNow', methods: ['GET'])]

    public function DateNow(Request $request, DepenseRepository $depenseRepository): Response
    {
        $DepenseByNom = $depenseRepository->searchdatenow();
        return $this->render('depense/index.html.twig', [
            'depenses' => $DepenseByNom,
        ]);
    }
//tri
    #[Route('/order_By_Date', name: 'order_By_Date', methods: ['GET'])]

    public function order_By_Date(Request $request, DepenseRepository $depenseRepository): Response
    {
        $DepenseByDate = $depenseRepository->order_By_Date();

        return $this->render('depense/index.html.twig', [
            'depenses' => $DepenseByDate,
        ]);

    }

    #[Route('/order_By_Montant', name: 'order_By_Montant', methods: ['GET'])]

    public function order_By_Montant(Request $request, DepenseRepository $depenseRepository): Response
    {
        $DepenseByMontant = $depenseRepository->order_By_Montantd();

        return $this->render('depense/index.html.twig', [
            'depenses' => $DepenseByMontant,
        ]);
    }


    #[Route('/new', name: 'app_depense_new', methods: ['GET', 'POST'])]
    public function new(Request $request, DepenseRepository $depenseRepository, BudgetRepository $budgetRepository): Response
    {
        $montant = 0;
        $depense = new Depense($montant);
        $form = $this->createForm(DepenseType::class, $depense);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            // Vérification du budget salaire
            // $depense->checkBudgetSalaire();
            //$entityManager = $this->getDoctrine()->getManager();
            //$entityManager->persist($depense);
            //$entityManager->flush();
           // $id_budget  = $request->request->get('id_budget');
            $id_bud = $depense->getIdBudget();
            $budget = $budgetRepository->findOneBy(['id' => $id_bud]);
            $salaire =$budget->getBudgetSalaire();
            $matrl = $budget->getBudgetMateriel();
            $service =$budget->getBudgetService();
            $total = $budget->getBudget();


            switch ($depense->getCategorie()) {
                case 'budget_salaire':
                    $budget->setBudget_Salaire($salaire - $depense->getMontant());
                    break;
                case 'budget_materiel':
                    $budget->setBudget_Materiel($matrl - $depense->getMontant());
                    break;
                case 'budget_service':
                    $budget->setBudget_Service($service - $depense->getMontant());
                    break;
                default:
                    throw new \InvalidArgumentException('La catégorie fournie est invalide.');
            }

            $budget->setBudget($total - $depense->getMontant());

            $budgetRepository->save($budget, true);

            $depenseRepository->save($depense, true);

            return $this->redirectToRoute('app_depense_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('depense/new.html.twig', [
            'depense' => $depense,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_depense_show', methods: ['GET'])]
    public function show(Depense $depense): Response
    {
        return $this->render('depense/show.html.twig', [
            'depense' => $depense,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_depense_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Depense $depense, DepenseRepository $depenseRepository): Response
    {
        $form = $this->createForm(DepenseType::class, $depense);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $depenseRepository->save($depense, true);

            return $this->redirectToRoute('app_depense_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('depense/edit.html.twig', [
            'depense' => $depense,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_depense_delete', methods: ['POST'])]
    public function delete(Request $request, Depense $depense, DepenseRepository $depenseRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$depense->getId(), $request->request->get('_token'))) {
            $depenseRepository->remove($depense, true);
        }

        return $this->redirectToRoute('app_depense_index', [], Response::HTTP_SEE_OTHER);
    }
}
