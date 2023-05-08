<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Doctrine\ORM\EntityManagerInterface;
use App\Entity\User;
use App\Form\LoginType;
use Symfony\Component\HttpFoundation\Session\Session;
use App\Form\UserType;
use App\Repository\UserRepository;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Security\Core\Validator\Constraints\UserPassword;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;




/**
 * @Route("/user")
 */
class UserController extends AbstractController
{
    /**
     * @Route("/", name="app_user_index")
     * Methods=({"GET"})
     */
    public function index(Session $session, UserRepository $us): Response
    {   
       
        $currentUser = $us->findOneBy(['id' => $session->get('id')]);
        
        $this->addFlash('success', 'Bienvenue '.$currentUser->getNomprenom());
        if ($this->denyAccessUnlessGranted('ROLE_RHEMP')){
        
        
            return $this->render('user/index.html.twig', [
                'currentUser' => $currentUser
            ]);
        }
        else{
            return $this->redirectToRoute('app_home');
        } 
    }

    /**
     * @Route("/new_employée", name="app_employe_addForm")
     * Methods=({"GET","POST"})
     */
    public function new(Request $request, EntityManagerInterface $entityManager, Session $session): Response
    {
        $user = new User();
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $user->setRole('Employé');
            $entityManager->persist($user);
            $entityManager->flush();

            $this->addFlash('info', 'User Added successfuly!');
            return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
        }
        return $this->render('user/new.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }
        /**
     * @Route("/newRHemployée", name="app_RHemploye_addForm")
     * Methods=({"GET","POST"})
     */
    public function newRHemployée(Request $request, EntityManagerInterface $entityManager, Session $session): Response
    {
        $user = new User();
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $user->setRole('RHEmployé');
            $entityManager->persist($user);
            $entityManager->flush();

            $this->addFlash('info', 'User Added successfuly!');
            return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
        }
        return $this->render('user/new.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }
        /**
     * @Route("/newRHresponsable", name="app_RHresponsable_addForm")
     * Methods=({"GET","POST"})
     */
    public function newRHresponsable(Request $request, EntityManagerInterface $entityManager, Session $session): Response
    {
        $user = new User();
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $user->setRole('RHresponsable');
            $entityManager->persist($user);
            $entityManager->flush();

            $this->addFlash('info', 'User Added successfuly!');
            return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
        }
        return $this->render('user/new.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/delete/{id}", name="app_user_delete")
     * Methods=({"DELETE","GET"})
     *
     */
    public function delete($id)
    {

        $user = new User();
        $user = $this->getDoctrine()->getRepository(User::class)->find($id);
        $em = $this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();
        $response = new Response();
        $response->send();
        $this->addFlash('info', 'User deleted successfuly!');
        return $this->redirectToRoute('app_user_index');
    }
    /**
     * @Route("/edit/{id}", name="app_user_edit")
     * Methods=({"GET","POST"})
     */
    public function edit(Request $request, EntityManagerInterface $entityManager, $id)
    {
        $user = new User();
        $user = $this->getDoctrine()->getRepository(User::class)->find($id);
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            
            $user1 = $form->getData();
            $entityManager->flush();
            $this->addFlash('info', 'User updated successfuly!');
            return $this->redirectToRoute('app_user_index');
        }
        return $this->render('user/edit.html.twig', [
            'form' => $form->createView(),
            'profile_image' => $user->getFace()
        ]);
    }

    /**
     * @Route("/login", name="app_user_login" )
     * Methods=({"POST"})
     * @return JsonResponse
     */
    public function login( Request $request,Session $session):Response {
        $user = new User();
        //return new JsonResponse('Deleted');&
          
            $email=$request->request->get('email');
            $password=$request->request->get('password');
            if ($email==null || $password==null){
                //$error = $authenticationUtils->getLastAuthenticationError();
                return new JsonResponse([
                    'error' => "Fill the inputs "
                ], 401);
            }
            
            $userSearched = $this->getDoctrine()->getRepository(User::class)->findOneBy(['email' => $email]);
            if($userSearched== null){
                //$error = $authenticationUtils->getLastAuthenticationError();
                return new JsonResponse([
                    'error' => "Email introuvable"
                ], 401);
            }else{
                return new JsonResponse([
                    'error' => "Password incorrect"
                ], 401);
            }
    }
    
    /**
     * @Route("/logout", name="app_user_logout")
     * Methods=({"GET"})
     */
    public function Logout(Request $request): Response
    {
        $session = $request->getSession();
        $session->clear();
        return $this->redirectToRoute('app_front_home');
    }

    

    /**
     * @Route("/profile", name="app_user_profile")
     * Methods=({"GET"})
     */
    public function showConnected(Session $session, UserRepository $us): Response
    {
        $user = $us->findOneBy(['id' => $session->get('id')]);
        return $this->render('user/show.html.twig', [
            'user' => $user
        ]);
    }

    /**
     * @Route("/{id}", name="app_user_show")
     * Methods=({"GET"})
     */
    public function show(User $user): Response
    {
        return $this->render('user/show.html.twig', [
            'user' => $user,
        ]);
    }


    /**
     * @Route("/ajax/getall/{nomsociete}", name="app_user_getall", methods={"GET"})
     * @return JsonResponse
     */
    public function getAllajax(Request $request,NormalizerInterface $normalizer,)
    {
            $em = $this->getDoctrine()->getManager();
            $data = $em->getRepository(User::class)
            ->findBy(['nomsociete'=> ($nomsociete)]);
            $jsoncontent=$normalizer->normalize($data,'json',['groups'=>'post:read']);
            return new JsonResponse($jsoncontent);   
    }

    /**
     * @Route("/ajax/edit/{iduser}", name="app_user_editajax", methods={"GET"})
     * @return JsonResponse
     */
    public function EditAjax(Request $request,NormalizerInterface $normalizer,$iduser)
    {
            $em = $this->getDoctrine()->getManager();
            $data = $em->getRepository(User::class)
            ->find($id);
            $data->setNom($request->get("nom"));
            $data->setPrenom($request->get("prenom"));
            $data->setEmail($request->get("email"));
            $data->setPassword($request->get("password"));
            $data->setRoles($request->get("role"));
            $em->flush();
            return new JsonResponse("Done");   
    }

    /**
     * @Route("/ajax/add/", name="app_user_addajax", methods={"GET"})
     * @return JsonResponse
     */
    public function AddAjax(Request $request,NormalizerInterface $normalizer)
    {
            $em = $this->getDoctrine()->getManager();
            $data=new User();
            $data->setNom($request->get("nom"));
            $data->setPrenom($request->get("prenom"));
            $data->setEmail($request->get("email"));
            $data->setPassword($request->get("password"));
            $data->setRoles($request->get("role"));
            $em = $this->getDoctrine()->getManager();
            $em->persist($data);
            $em->flush();
            return new JsonResponse("Done");   
    }

    /**
     * @Route("/ajax/delete/{iduser}", name="app_user_deleteajax", methods={"DELETE","GET"})
     * @return JsonResponse
     */
    public function ajaxDeleteItemAction(Request $request,$user_id)
    {
        try{
            $id = $user_id;
            $em = $this->getDoctrine()->getManager();
            $evenement = $em->getRepository(User::class)->find($id);
            $em->remove($evenement);
            $em->flush();       
            return new JsonResponse('Deleted');
        }
        catch(\Exception $e){
            return new JsonResponse([
                'error' => $id
            ], 500);

        }    
    }

    /**
     * @Route("/ajax/login/", name="app_user_loginajax", methods={"GET"})
     * @return JsonResponse
     */
    public function Ajaxlogin(Request $request,NormalizerInterface $normalizer,UserRepository $us)
    {
            $data = new User();
        //return new JsonResponse('Deleted');&
          
            $email=$request->get('email');
            $password=$request->get('password');
            
            
            $userSearched = $this->getDoctrine()->getRepository(User::class)->findOneBy(['email' => $email]);
            if($userSearched== null){
                //Mail introuvable;
                return new JsonResponse(0);
            }else{
                
                return new JsonResponse(0);
            }
    }
}