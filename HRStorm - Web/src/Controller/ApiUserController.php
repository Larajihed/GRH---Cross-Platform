<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizableInterface;
use App\Entity\User;
use App\Repository\UserRepository;

class ApiUserController extends AbstractController
{
    #[Route('/api/user', name: 'app_api_user')]
    public function index(UserRepository $userRepositoryr, NormalizableInterface $ss): Response
    {
        return $this->render('api_user/index.html.twig', [
            'controller_name' => 'ApiUserController',
        ]);
    }
}
