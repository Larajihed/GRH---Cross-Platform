<?php

namespace App\Controller;

use App\Entity\Budget;
use App\Entity\Depense;
use App\Entity\MyOptions;
use App\Entity\Dompdf;
use App\Form\SearchBudgetType;
use App\Form\BudgetTypedate;
use App\Form\BudgetType;
use App\Repository\BudgetRepository;
use App\Repository\DepenseRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\OptionsResolver\OptionsResolver;
//use Dompdf\Dompdf;
use Dompdf\Bundle\DompdfBundle;
//use App\Options\MyOptions;
use Symfony\Component\OptionsResolver\Options;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\Serializer\SerializerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Knp\Component\Pager\Paginator;
use App\Service\PdfService;
use App\Service\BudgetManager;
use Doctrine\Persistence\ManagerRegistry;
use Psr\Log\LoggerInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;
use Symfony\Component\HttpFoundation\RedirectResponse;
use App\Event\ListAllBudgetsEvent;
use Symfony\Component\HttpFoundation\Session\Session;
use App\Repository\UserRepository;



#[Route('/budget')]
class BudgetController extends AbstractController
{   
    private $dispatcher;

    public function __construct(EventDispatcherInterface $dispatcher)
    {
        $this->dispatcher = $dispatcher;
    }

    #[Route('/', name: 'app_budget_index', methods: ['GET'])]
    public function index(BudgetRepository $budgetRepository,Session $session,UserRepository $us): Response
    {   
        $currentUser = $us->findOneBy(['id' => $session->get('id')]);
        
        $piechartData = $budgetRepository->countByBudget();
        $linechartData = $budgetRepository->countByMonth();
        $data = $budgetRepository->getBudgetData();

        return $this->render('budget/index.html.twig', [
            'budgets' => $budgetRepository->findAll(),
            'data' => $data,
            'piechartData' => $piechartData,
            'linechartData' => $linechartData,
        ]);
    }

    #[Route('/searchquery', name: 'search_budget')]
    public function findBudget(Request $request): Response
    {
        $name = $request->request->get('searchTerm');

        if (!$name) {
            $budgets = $this->getDoctrine()->getRepository(Budget::class)->findAll();
        } else {
            $budgets = $this->getDoctrine()->getRepository(Budget::class)->findBudget($name);
        }

        $tableHtml = $this->renderView('budget/_table.html.twig', [
            'budgets' => $budgets,
            'searchTerm' => $name
        ]);

        return new Response($tableHtml);

    }

    #[Route('/search', name: 'search_budget_page')]
    public function findBudgets(Request $request) :Response
    {
        $name = $request->request->get('searchTerm');

        if (!$name) {
            $budgets = $this->getDoctrine()->getRepository(Budget::class)->findAll();
        } else {
            $budgets = $this->getDoctrine()->getRepository(Budget::class)->findBudget($name);
        }


        return $this->render('budget/search.html.twig', [
            'budgets' => $budgets,
            'searchTerm' => $name
        ]);
    }


    #[Route('/budgetStatistics', name: 'budgetStatistics', methods: ['GET'])]
    public function budgetStatistics(Request $request,BudgetRepository $budgetRepository)
    {
        $startDate = new \DateTime('2023-01-01');
        $endDate = new \DateTime('2023-02-28');
        $budgetRepository = $this->getDoctrine()->getRepository(Budget::class);
        $BudgetByDate = $budgetRepository->getBudgetByDate($startDate, $endDate);
        $piechartData = $budgetRepository->countByBudget();
        $linechartData = $budgetRepository->countByMonth();
        $data = $budgetRepository->getBudgetData();
        return $this->render('budget/index.html.twig', [
            'budgets' => $BudgetByDate,
            'data' => $data,
            'piechartData' => $piechartData,
            'linechartData' => $linechartData,
        ]);
    }


    #[Route('/pdf', name: 'pdf')]
    public function pdf(Budget $budgets = null, PdfService $pdf)
    {
        $html = $this->render('budget/pdf.html.twig', ['budgets' => $budgets]);
        $pdf->showPdfFile($html);
    }

    #[Route('/{id<\d+>}', name: 'detail')]
    public function detail(Budget $budget = null): Response
    {
        if (!$budget) {
            $this->addFlash('error', "La budget n'existe pas ");
            return $this->redirectToRoute('app_budget_index');
        }

        return $this->render('budget/detail.html.twig', ['budgets' => $budget]);
    }


    #[Route('/DateNow', name: 'DateNow', methods: ['GET'])]
    public function DateNow(Request $request, BudgetRepository $budgetRepository): Response
    {
        $BudgetBybudget = $budgetRepository->searchdatenow();
        return $this->render('budget/index.html.twig', [
            'budgets' => $BudgetBybudget,
        ]);
    }

//tri
    #[Route('/order_By_Dateb', name: 'order_By_Dateb', methods: ['GET'])]
    public function order_By_Dateb(Request $request, BudgetRepository $budgetRepository): Response
    {
        $BudgetByDate = $budgetRepository->order_By_Dateb();
        $piechartData = $budgetRepository->countByBudget();
        $linechartData = $budgetRepository->countByMonth();
        $data = $budgetRepository->getBudgetData();

        return $this->render('budget/index.html.twig', [
            'budgets' => $BudgetByDate,
            'data' => $data,
            'piechartData' => $piechartData,
            'linechartData' => $linechartData,
        ]);

    }

    #[Route('/order_By_budget', name: 'order_By_budget', methods: ['GET'])]
    public function order_By_budget(Request $request, BudgetRepository $budgetRepository): Response
    {
        $BudgetBybudget = $budgetRepository->order_By_budget();
        $piechartData = $budgetRepository->countByBudget();
        $linechartData = $budgetRepository->countByMonth();
        $data = $budgetRepository->getBudgetData();
        return $this->render('budget/index.html.twig', [
            'budgets' => $BudgetBybudget,
            'data' => $data,
            'piechartData' => $piechartData,
            'linechartData' => $linechartData,
        ]);
    }


    //recherche
    #[Route('/listBudgetWithSearchbudget', name: 'listBudgetWithSearchbudget')]
    public function listBudgetWithSearchbudget(Request $request, BudgetRepository $budgetRepository)
    {
        $budget = $budgetRepository->findAll();
        //search
        $searchForm = $this->createForm(SearchBudgetType::class);
        $searchForm->add("Recherche", SubmitType::class);
        $searchForm->handleRequest($request);
        if ($searchForm->isSubmitted()) {
            $budget_bud = $searchForm['budget_bud']->getData();
            $resultat = $budgetRepository->searchbudget($budget_bud);
            return $this->render('budget/searchBudget.html.twig', array(
                "budgets" => $resultat,
                "searchbudget" => $searchForm->createView()));
        }
        return $this->render('budget/searchBudget.html.twig', array(
            "budgets" => $budget,
            "searchBudget" => $searchForm->createView()));
    }

    #[Route('/listBudgetWithSearchdate', name: 'listBudgetWithSearchdate')]
    public function listBudgetWithSearchdate(Request $request, BudgetRepository $budgetRepository)
    {
        $budget = $budgetRepository->findAll();
        //search
        $searchForm = $this->createForm(BudgetTypedate::class);
        $searchForm->add("Recherche", SubmitType::class);
        $searchForm->handleRequest($request);
        if ($searchForm->isSubmitted()) {
            $date = $searchForm['date']->getData();
            $resultat = $budgetRepository->searchdate($date);
            return $this->render('budget/searchBudget.html.twig', array(
                "budgets" => $resultat,
                "searchBudget" => $searchForm->createView()));
        }
        return $this->render('budget/searchBudget.html.twig', array(
            "budgets" => $budget,
            "searchBudget" => $searchForm->createView()));
    }


    #[Route('/new', name: 'app_budget_new', methods: ['GET', 'POST'])]
    public function new(Request $request, BudgetRepository $budgetRepository, BudgetManager $budgetManager): Response
    {
        $budget = new Budget();
        $form = $this->createForm(BudgetType::class, $budget);

        $form->handleRequest($request);
        //$formattedDate = $date->format('m,y');
        if ($form->isSubmitted() && $form->isValid()) {
            $categorie = $request->request->get('categorie');
            $montant = $request->request->get('montant');


            $budgetRepository->save($budget, true);
            // récupérer les données du formulaire
            $categorie = $request->request->get('categorie');
            $montant = $request->request->get('montant');


          //  $categorieAsString = (string) $categorie;
            //$montantAsFloat = (float) $categorie;

            // appeler la fonction soustraireBudget pour déduire le montant du budget
        //    $budgetManager->soustraireBudget($categorieAsString, $montantAsFloat);

            return $this->redirectToRoute('app_budget_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('budget/new.html.twig', [
            'budget' => $budget,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_budget_show', methods: ['GET'])]
    public function show(Budget $budget): Response
    {
        return $this->render('budget/show.html.twig', [
            'budget' => $budget,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_budget_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Budget $budget, BudgetRepository $budgetRepository): Response
    {
        $form = $this->createForm(BudgetType::class, $budget);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $budgetRepository->save($budget, true);

            return $this->redirectToRoute('app_budget_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('budget/edit.html.twig', [
            'budget' => $budget,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_budget_delete', methods: ['POST'])]
    public function delete(Request $request, Budget $budget, BudgetRepository $budgetRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $budget->getId(), $request->request->get('_token'))) {
            $budgetRepository->remove($budget, true);
        }

        return $this->redirectToRoute('app_budget_index', [], Response::HTTP_SEE_OTHER);
    }


    #[Route('/AllBudgetJSON', name: 'AllBudgetJSON')]
    public function AllBudgetJSON(BudgetRepository $budgetRepository, SerializerInterface $serializerInterface)
    {
        //$repository= $this->getDoctrine()->getRepository(Budget::class);
        $Budget = $budgetRepository->findAll();
        $json = $serializerInterface->normalize($Budget, 'json', ['groups' => 'budgets']);
        return new Response(json_encode($json));
    }

    #[Route('/AddBudgetJSON/{id}', name: 'AddBudgetJSON')]
    public function AddBudgetJSON(Request $request, NormalizerInterface $Normalizer)
    {
        $em = $this->getDoctrine()->getManager();
        $Budget = new Budget();
        $Budget->setBudgetBudget($request->get('BudgetBudget'));
        $Budget->setDate($request->get('Date'));
        $Budget->setPrimeBudget($request->get('PrimeBudget'));
        $em->persist($Budget);
        $em->flush();
        $jsonContent = $Normalizer->normalize($Budget, 'json', ['groups' => 'post:read']);
        return new Response(json_encode($jsonContent));
    }


    #[Route('/UpdateBudgetJSON/{id}', name: 'UpdateBudgetJSON')]
    public function UpdateBudgetJSON($id, Request $request, NormalizerInterface $Normalizer)
    {
        $em = $this->getDoctrine()->getManager();
        $Budget = $this->getDoctrine()->getRepository(Budget::class)->find($id);
        $Budget->setBudgetBudget($request->get('BudgetBudget'));
        $Budget->setDate($request->get('Date'));
        $Budget->setPrimeBudget($request->get('PrimeBudget'));
        $em->flush();
        $jsonContent = $Normalizer->normalize($Budget, 'json', ['groups' => 'post:read']);
        return new Response("Update successfully" . json_encode($jsonContent));
    }


    #[Route('/DeleteBudgetJSON/{id}', name: 'DeleteBudgetJSON', methods: ['POST'])]
    public function DeleteBudgetJSON($id, Request $request, NormalizerInterface $Normalizer)
    {
        $em = $this->getDoctrine()->getManager();
        $Budget = $this->getDoctrine()->getRepository(Budget::class)->find($id);
        $em->remove($Budget);
        $em->flush();
        $jsonContent = $Normalizer->normalize($Budget, 'json', ['groups' => 'post:read']);
        return new Response("Delete successfully" . json_encode($jsonContent));
    }

}
