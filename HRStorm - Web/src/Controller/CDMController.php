<?php

namespace App\Controller;

use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\User;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Encoder\XmlEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use App\Repository\UserRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

/**
 * Summary of UserServiceController
 */
class CDMController extends AbstractController
{
   
    #[Route('/Codenameone/service/RHresponsable/', name: 'app_reg_vet')]

    public function Register(UserRepository $repository ,ManagerRegistry $doctrine, Request $request,UserPasswordHasherInterface $passwordHasher,SerializerInterface $serializer)
    {   
        $email = $request->query->get("email");
        $password = $request->query->get("password");
        $nom = $request->query->get("nom");
        $prenom = $request->query->get("prenom");
        $nomsociete = $request->query->get("nomsociete");

        $user = new User();
        $hashedPassword = $passwordHasher->hashPassword(
            $user,
            $password
        );
        
        if(!filter_var($email, FILTER_VALIDATE_EMAIL)){
            return new Response("email invalid.");
        }
        $user->setEmail($email);
        $user->setNom($nom);
        $user->setPrenom($prenom);
        $user->setPassword($hashedPassword);
          // Set their role
        $user->setRoles(['ROLE_RESPONSABLE']);
        $user->setNomsociete($nomsociete);


        
       
        try{
            $repository->save($user, true);
            

            return new JsonResponse("account is created", 200);
        }catch(\Exception $ex){
            return new Response("execption".$ex->getMessage());
        }
    }


    
    

    
    #[Route('/Codenameone/service/login/', name: 'service_login')]   
    public function LoginUser(Request $request, EntityManagerInterface $entityManager, UserPasswordHasherInterface $PasswordHasher): JsonResponse
{
    $username=$request->query->get("email");
    $password=$request->query->get("password");
    $user=$entityManager->getRepository(User::class)->findOneByEmail($username);
    if ($user) {
        if ($PasswordHasher->isPasswordValid($user, $password)) {
            return $this->json(['User' => $user, 'Error'=>""]);    
         }
    }else{
        return $this->json(['User' => '', 'Error'=>"No account matches this username"]);
    }
}
}