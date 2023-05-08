<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\CongeRepository;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\Conge;
use Symfony\Component\Security\Core\Security;

use Symfony\Component\HttpFoundation\JsonResponse;
class CongesController extends AbstractController
{
    #[Route('/conges', name: 'app_conges')]
    public function conges(CongeRepository $congeRepository ,SerializerInterface $serializer, NormalizerInterface $normalizer)
  {
      $conges = $congeRepository->findAll();
    //    $congesNormalises = $normalizer->normalize($conges, 'json');
    //    $json = json_encode($congesNormalises);
      $congesNormalises = $normalizer->normalize($conges,'json' ,['groups' =>"conges"]);
      $json = $serializer-> serialize($conges, 'json' , ['groups' => "conges"]);
       $json = json_encode($congesNormalises);

      return new Response($json);

    }


    #[Route('/conges/{id}', name: 'app_conges_show')]
    public function conges_get_by_id($id, NormalizerInterface $normalizer , congeRepository $repo)
    {
      $conges=$repo->find($id);
      $congesNormalises = $normalizer->normalize($conges,'json' ,['groups' =>"conges"]);
      return new Response(json_encode($congesNormalises));
    }

    #[Route('addcongesJson/new', name: 'app_conges_new',methods: ['POST'])]
  public function addcongesJson(Request $req, NormalizerInterface $Normalizer,Security $security)
  {
    $em = $this->getDoctrine()->getManager();
    $conges = new conge();
    $user = $security->getUser();
    dump($user);die;

    $conges->setCategorie($req->get('categorie'));
    $conges->setDescription($req->get('description'));
    $conges->setDebut($req->get('debut'));
    $conges->setFin($req->get('fin'));

    $em->persist($conges);
    $em->flush();
    $jsonContent = $Normalizer->normalize($conges, 'json',['groups'=>'conges']);
    return new Response(json_encode($jsonContent));
  }

  #[Route('updatecongesJson/{id}', name: 'app_conges_update')]
  public function updatepharmaciesJson(Request $req,$id,  NormalizerInterface $Normalizer  )
  {
    $em = $this->getDoctrine()->getManager();
    $conges = $em->getRepository(conge::class)->find($id);
    $conges->setCategorie($req->get('categorie'));
    $conges->setDescription($req->get('description'));
    $conges->setDebut($req->get('debut'));
    $conges->setFin($req->get('fin'));
    $em->persist($conges);
    $em->flush();
    $jsonContent = $Normalizer->normalize($conges, 'json',['groups'=>'conges']);
    return new Response(json_encode($jsonContent));
  }






  
  #[Route('deletecongesJson/{id}', name: 'app_conges_delete')]
  public function deletecongesJson(Request $req,$id,  NormalizerInterface $Normalizer)
  {
    $em = $this->getDoctrine()->getManager();
    $conges = $em->getRepository(conge::class)->find($id);
    $em->remove($conges);
    $em->flush();
    $jsonContent = $Normalizer->normalize($conges, 'json',['groups'=>'conges']);
    return new Response("ph suppri".json_encode($jsonContent));
  }

}
