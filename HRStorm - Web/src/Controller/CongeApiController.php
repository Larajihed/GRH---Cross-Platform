<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class CongeApiController extends AbstractController
{
    #[Route('/conge/api', name: 'app_conge_api')]
    public function index(): Response
    {
        return $this->render('conge_api/index.html.twig', [
            'controller_name' => 'CongeApiController',
        ]);
    }
}
