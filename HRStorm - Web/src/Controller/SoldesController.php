<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class SoldesController extends AbstractController
{
    #[Route('/soldes', name: 'app_soldes')]
    public function index(): Response
    {
        return $this->render('soldes/index.html.twig', [
            'controller_name' => 'SoldesController',
        ]);
    }
}
