<?php

namespace App\Controller;
use App\Repository\EvaluationRepository;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\User;
use Symfony\Component\Security\Core\Security;
use App\Controller\UserRepository;

class EmployeeController extends AbstractController
{
    #[Route('/employee', name: 'app_employee')]
    public function index(): Response
    {
        return $this->render('employee/index.html.twig', [
            'controller_name' => 'EmployeeController',
        ]);
    }

    #[Route('/employee/evaluations', name: 'app_employee_evaluations')]
    public function getEvaluations(EvaluationRepository $evaluationRepository,Security $security
    ): Response
    {
        $user = $security->getUser();
         $evaluations = $evaluationRepository->findBy(['Employee' => $user] );
         foreach ($evaluations as $evaluation) {
             $missingCompetences = $evaluation->getMissingCompetences($evaluation);
           //  If there are missing competences, suggest a course for each one
         }
         return $this->render('employee/evaluations.html.twig', [
             'controller_name' => 'EmployeeController',
             'evaluations' =>$evaluations,
             'missingskills' => $missingCompetences
         ]);
     }
   

}