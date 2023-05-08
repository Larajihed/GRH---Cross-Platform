<?php

namespace App\Controller;

use App\Entity\Evaluation;
use App\Form\Evaluation1Type;
use App\Repository\EvaluationRepository;
use App\Repository\CompetenceRepository;
use App\Repository\PosteRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\ORM\EntityManagerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Knp\Component\Pager\Paginator;
use Symfony\Component\HttpFoundation\JsonResponse;
#[Route('/hremployee/evaluation')]
class EvaluationController extends AbstractController {

    private $paginator;

    public function __construct( PaginatorInterface $paginator)
    {
        $this->paginator = $paginator;
    }
    
    #[Route('/', name: 'app_evaluation_index', methods: ['GET'])]
    public function index(
        EvaluationRepository $evaluationRepository,
        CompetenceRepository $competenceRepository,
        PosteRepository $posteRepository,
        Request $request
    ): Response {
       //Get data
       
        $query = $evaluationRepository->createQueryBuilder('e')->getQuery();
        $page = $request->query->getInt('page', 1);
        $evaluations = $this->paginator->paginate($query, $page, 2);
        $postes = $posteRepository->findAll();
        $competences = $competenceRepository->findAll();
        $piechartData = $evaluationRepository->countByLevel();
        $linechartData = $evaluationRepository->countByMonth();
        
        // Loop through each evaluation and check missing competences
        foreach ($evaluations as $evaluation) {
            $missingCompetences = $evaluation->getMissingCompetences($evaluation);
            // If there are missing competences, suggest a course for each one
        }
    
        return $this->render('/hr_employee/evaluation/index.html.twig', [
            'evaluations' => $evaluations,
            'competences' => $competences, 
            'postes' => $postes, 
            'piechartData' => $piechartData,
            'linechartData' => $linechartData,
            'missingCompetences' => $missingCompetences,
        ]);
    }

    #[Route('/searchquery', name: 'search_evaluations')]
    public function findEvaluation(Request $request): Response
    {
        $name = $request->request->get('searchTerm');

        if (!$name) {
            $evaluations = $this->getDoctrine()->getRepository(Evaluation::class)->findAll();
        } else {
            $evaluations = $this->getDoctrine()->getRepository(Evaluation::class)->findEvaluation($name);
        }
        
        $tableHtml = $this->renderView('hr_employee/evaluation/_table.html.twig', [
            'evaluations' => $evaluations,
            'searchTerm' => $name
        ]);
    
        return new Response($tableHtml);
        
    
    }
    #[Route('/get', name: 'get_evaluations', methods: ['GET'])]
    public function getEvaluations(EvaluationRepository $evaluationRepository): JsonResponse
    {
        $evaluations = $evaluationRepository->findAll();
        $data = [];
    
        foreach ($evaluations as $evaluation) {
            $data[] = [
                'id' => $evaluation->getId(),
                'date' => $evaluation->getDate(),
                'commentaire' => $evaluation->getCommentaire(),
                'experience' => $evaluation->getExperience(),
                'level' => $evaluation->getLevel(),
                'poste' => [
                    'id' => $evaluation->getPoste()->getId(),
                    'nom' => $evaluation->getPoste()->getNom(),
                ],
                'competences' => $evaluation->getCompetences()->map(function ($competence) {
                    return [
                        'id' => $competence->getId(),
                        'nom' => $competence->getNom(),
                    ];
                })->toArray(),
            ];
        }
    
        return new JsonResponse($data);
    }
    


    #[Route('/search', name: 'search_evaluations_page')]
    public function findEvaluations(Request $request) :Response
    {
        $name = $request->request->get('searchTerm');

        if (!$name) {
            $evaluations = $this->getDoctrine()->getRepository(Evaluation::class)->findAll();
        } else {
            $evaluations = $this->getDoctrine()->getRepository(Evaluation::class)->findEvaluation($name);
        }
        
       
    
        return  $this->render('hr_employee/evaluation/search.html.twig', [
            'evaluations' => $evaluations,
            'searchTerm' => $name
        ]);
        
    
    }

    #[Route('/new', name: 'app_evaluation_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EvaluationRepository $evaluationRepository): Response
    {
        $evaluation = new Evaluation();
        $form = $this->createForm(Evaluation1Type::class, $evaluation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $evaluationRepository->save($evaluation, true);

            return $this->redirectToRoute('app_evaluation_index', [], Response::HTTP_SEE_OTHER);
        }
        
                return $this->renderForm('/hr_employee/evaluation/new.html.twig', [
                    'evaluation' => $evaluation,
                    'form' => $form,
                ]);
    }

    #[Route('/{id}', name: 'app_evaluation_show', methods: ['GET'])]
    public function show(Evaluation $evaluation): Response
    {
        return $this->render('/hr_employee/evaluation/show.html.twig', [
            'evaluation' => $evaluation,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_evaluation_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Evaluation $evaluation, EvaluationRepository $evaluationRepository): Response
    {
        $form = $this->createForm(Evaluation1Type::class, $evaluation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $evaluationRepository->save($evaluation, true);

            return $this->redirectToRoute('app_evaluation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('/hr_employee/evaluation/edit.html.twig', [
            'evaluation' => $evaluation,
            'form' => $form,
        ]);
    }

    
  
    #[Route('/{id}', name: 'app_evaluation_delete', methods: ['POST'])]
    public function delete(Request $request, Evaluation $evaluation, EvaluationRepository $evaluationRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$evaluation->getId(), $request->request->get('_token'))) {
            $evaluationRepository->remove($evaluation, true);
        }

        return $this->redirectToRoute('app_evaluation_index', [], Response::HTTP_SEE_OTHER);
    }

}
