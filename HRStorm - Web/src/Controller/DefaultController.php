<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Process\Process;

class DefaultController extends AbstractController
{

    #[Route('/chat', name: 'executeChat')] 
    public function executeChat(): Response
    {
        $process = new Process(['cmd', '/c', 'C:\\Users\\conta\\Desktop\\chat.cmd']);
        $process->run();

        if (!$process->isSuccessful()) {
            throw new \RuntimeException($process->getErrorOutput());
        }

        return $this->render('base.html.twig', [
            'controller_name' => 'DefaultController',           ]);
        }


    #[Route('/', name: 'app_default')]
    public function index(): Response
    {
        return $this->render('base.html.twig', [
            'controller_name' => 'DefaultController',
        ]);
    }

    #[Route('/dashboard', name: 'app_dashboard')]
    public function indexDashboard(): Response
    {
        return $this->render('employee/dashboard.html.twig', [
            'controller_name' => 'DefaultController',
        ]);
    }
}
