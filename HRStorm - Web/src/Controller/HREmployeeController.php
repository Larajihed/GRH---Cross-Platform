<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class HREmployeeController extends AbstractController
{
    #[Route('/hremployee', name: 'app_h_r_employee')]
    public function index(): Response
    {
        return $this->render('hr_employee/index.html.twig', [
            'controller_name' => 'HREmployeeController',
        ]);
    }
}
