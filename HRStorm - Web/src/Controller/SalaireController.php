<?php

namespace App\Controller;
use App\Entity\Salaire;
use App\Form\SalaireType;
use App\Controller\SearchSalaireType;
use App\Repository\BudgetRepository;
use App\Repository\SalaireRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bridge\Twig\Mime\TemplatedEmail;


#[Route('/employee/salaire'), IsGranted('ROLE_USER')]
class SalaireController extends AbstractController
{
    #[Route('/', name: 'app_salaire_index', methods: ['GET'])]
    public function index(SalaireRepository $salaireRepository): Response
    {

        return $this->render('salaire/index.html.twig', [
            'salaires' => $salaireRepository->findAll(),
        ]);
    }


    #[Route('/searchquery', name: 'search_salaire')]
    public function findSalaire(Request $request): Response
    {
        $namee = $request->request->get('searchTerm');

        if (!$namee) {
            $salaires = $this->getDoctrine()->getRepository(Salaire::class)->findAll();
        } else {
            $salaires = $this->getDoctrine()->getRepository(Salaire::class)->findSalaire($namee);
        }

        $tableHtml = $this->renderView('salaire/_table.html.twig', [
            'salaires' => $salaires,
            'searchTerm' => $namee
        ]);

        return new Response($tableHtml);

    }

    #[Route('/search', name: 'search_salaire_page')]
    public function findSalaires(Request $request) :Response
    {
        $name = $request->request->get('searchTerm');

        if (!$name) {
            $salaires = $this->getDoctrine()->getRepository(Salaire::class)->findAll();
        } else {
            $salaires = $this->getDoctrine()->getRepository(Salaire::class)->findSalaire($name);
        }


        return $this->render('salaire/search.html.twig', [
            'salaires' => $salaires,
            'searchTerm' => $name
        ]);
    }


    #[Route('/order_By_date', name: 'order_By_date', methods: ['GET'])]

    public function order_By_date(Request $request, SalaireRepository $salaireRepository): Response
    {
        $SalaireByDate = $salaireRepository->order_By_date();

        return $this->render('salaire/index.html.twig', [
            'salaires' => $SalaireByDate,
        ]);

    }

    #[Route('/order_By_montant', name: 'order_By_montant', methods: ['GET'])]

    public function order_By_montant(Request $request, SalaireRepository $salaireRepository): Response
    {
        $SalaireByMontant = $salaireRepository->order_By_montant();

        return $this->render('salaire/index.html.twig', [
            'salaires' => $SalaireByMontant,
        ]);
    }

    #[Route('/listSalaireWithSearchid', name: 'listSalaireWithSearchid')]

    public function listSalaireWithSearchid(Request $request,  SalaireRepository $salaireRepository)
    {
        $salaire= $salaireRepository->findAll();
        //search

        $form = $this->createForm(SearchSalaireType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $id_user = $form->getData();
            $SalaireById = $salaireRepository->findbyid($id_user);

            return $this->render('salaire/searchSalaire.html.twig', [
                'salaires' => $SalaireById,
            ]);
        }

        return $this->render('salaire/searchSalaire.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/new', name: 'app_salaire_new', methods: ['GET', 'POST'])]
    public function new(Request $request, SalaireRepository $salaireRepository): Response
    {
        $salaire = new Salaire();
        $form = $this->createForm(SalaireType::class, $salaire);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $salaireRepository->save($salaire, true);

            return $this->redirectToRoute('app_salaire_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('salaire/new.html.twig', [
            'salaire' => $salaire,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_salaire_show', methods: ['GET'])]
    public function show(Salaire $salaire): Response
    {
        return $this->render('salaire/show.html.twig', [
            'salaire' => $salaire,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_salaire_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Salaire $salaire, SalaireRepository $salaireRepository): Response
    {
        $form = $this->createForm(SalaireType::class, $salaire);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $salaireRepository->save($salaire, true);

            return $this->redirectToRoute('app_salaire_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('salaire/edit.html.twig', [
            'salaire' => $salaire,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_salaire_delete', methods: ['POST'])]
    public function delete(Request $request, Salaire $salaire, SalaireRepository $salaireRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$salaire->getId(), $request->request->get('_token'))) {
            $salaireRepository->remove($salaire, true);
        }

        return $this->redirectToRoute('app_salaire_index', [], Response::HTTP_SEE_OTHER);
    }
}
