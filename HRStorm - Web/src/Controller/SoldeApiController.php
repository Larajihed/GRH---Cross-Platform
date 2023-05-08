<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class SoldeApiController extends AbstractController
{
    #[Route('/solde/api', name: 'app_solde_api')]
    public function index(): Response
    {
        return $this->render('solde_api/index.html.twig', [
            'controller_name' => 'SoldeApiController',
        ]);
    }
}
