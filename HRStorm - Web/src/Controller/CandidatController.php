<?php

namespace App\Controller;
use App\Entity\Recrutement;
use App\Entity\Candidat;
use App\Form\Candidat1Type;
use App\Repository\CandidatRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bridge\Doctrine\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;

use Symfony\Component\String\Slugger\SluggerInterface;
use App\Entity\User;
use Symfony\Component\Security\Core\Security;
use Symfony\Component\VarDumper\Cloner\ClonerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Dompdf\Dompdf;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\HttpFoundation\Session\Session;
use App\Repository\UserRepository;

#[Route('/candidat')]
class CandidatController extends AbstractController
{
    #[Route('/', name: 'app_candidat_index', methods: ['GET'])]
    public function index(CandidatRepository $candidatRepository,PaginatorInterface $paginator, Request $request,Session $session,UserRepository $us): Response
    {   $currentUser = $us->findOneBy(['id' => $session->get('id')]);
        if ($this->denyAccessUnlessGranted('ROLE_RHEMP')){
             return $this->redirectToRoute('/login');
 
        };
        $candidats = $candidatRepository->findAll();
        $tests = $paginator->paginate(
            $candidats,
            $request->query->getInt('page', 1),
            5 // number of items per page
        );
        return $this->render('candidat/index.html.twig', [
            'candidats' => $tests,
        ]);
    }


    #[Route('/searchquery', name: 'search_candidat')]
    public function findBudget(Request $request): Response
    {
        $name = $request->request->get('searchTerm');

        if (!$name) {
            $candidats = $this->getDoctrine()->getRepository(Candidat::class)->findAll();
        } else {
            $candidats = $this->getDoctrine()->getRepository(Candidat::class)->findCandidat($name);
        }

        $tableHtml = $this->renderView('candidat/_table.html.twig', [
            'candidats' => $candidats,
            'searchTerm' => $name
        ]);

        return new Response($tableHtml);

    }

    #[Route('/search', name: 'search_candidat_page')]
    public function findCandidats(Request $request) :Response
    {
        $name = $request->request->get('searchTerm');

        if (!$name) {
            $candidats = $this->getDoctrine()->getRepository(Candidat::class)->findAll();
        } else {
            $candidats = $this->getDoctrine()->getRepository(Candidat::class)->findCandidat($name);
        }


        return $this->render('candidat/search.html.twig', [
            'candidats' => $candidats,
            'searchTerm' => $name
        ]);
    }
    #[Route('/download-pdf', name: 'download_pdf')]
    public function downloadPdfAction(Request $request, EntityManagerInterface $em)
    {
        $candidat_id = $request->query->get('candidat_id');


        $c = $em->getRepository(Candidat::class)->find($candidat_id);
        // Get the HTML content with the navbar
        $html = $this->renderView('candidat/candidatpdf.html.twig', [
            // Add any variables needed for the navbar
            'candidat' => $c,
        ]);

        // Create a new instance of Dompdf
        $dompdf = new Dompdf();

        // Load the HTML into Dompdf
        $dompdf->loadHtml($html);

        // Set the paper size and orientation
        $dompdf->setPaper('A4', 'portrait');

        // Render the PDF
        $dompdf->render();

        // Generate the PDF file name
        $filename = 'myfile.pdf';

        // Get the PDF content as a string
        $pdfContent = $dompdf->output();

        // Return a response with the PDF content as a file download
        return new Response(
            $pdfContent,
            200,
            array(
                'Content-Type' => 'application/pdf; charset=utf-8',
                'Content-Disposition' => 'attachment; filename="'.$filename.'"'
            )
        );
    }

    #[Route('/new', name: 'app_candidat_new', methods: ['GET', 'POST'])]
    public function new(Request $request, CandidatRepository $candidatRepository, SluggerInterface  $slugger): Response
    {
        $candidat = new Candidat();
        $form = $this->createForm(Candidat1Type::class, $candidat);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $candidat->setEtat("0");
           // dump($candidat);
           // die;
            $cv = $form->get('cv')->getData();

            // this condition is needed because the 'brochure' field is not required
            // so the PDF file must be processed only when a file is uploaded
            if ($cv) {
                $originalFilename = pathinfo($cv->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = time().'-'.uniqid().'.'.$cv->guessExtension();

                // Move the file to the directory where brochures are stored
                try {
                    $cv->move(
                        $this->getParameter('candidat_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // updates the 'brochureFilename' property to store the PDF file name
                // instead of its contents
                $candidat->setCv($newFilename);
            }
            $candidatRepository->save($candidat, true);

            return $this->redirectToRoute('app_default', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('candidat/new.html.twig', [
            'candidat' => $candidat,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_candidat_show', methods: ['GET'])]
    public function show(Candidat $candidat): Response
    {
        return $this->render('candidat/show.html.twig', [
            'candidat' => $candidat,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_candidat_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Candidat $candidat, CandidatRepository $candidatRepository): Response
    {
        $form = $this->createForm(Candidat1Type::class, $candidat);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $candidatRepository->save($candidat, true);

            return $this->redirectToRoute('app_candidat_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('candidat/edit.html.twig', [
            'candidat' => $candidat,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_candidat_delete', methods: ['POST'])]
    public function delete(Request $request, Candidat $candidat, CandidatRepository $candidatRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$candidat->getId(), $request->request->get('_token'))) {
            $candidatRepository->remove($candidat, true);
        }

        return $this->redirectToRoute('app_candidat_index', [], Response::HTTP_SEE_OTHER);
    }
    #[Route('/refuser/{id}', name: 'app_candidat_refuser',methods: ['GET', 'POST'])]
    public function refuser(Request $request, Candidat $candidat, candidatRepository $candidatRepository): Response

    {

        $candidat->setEtat("2");
        $candidatRepository->save($candidat, true);


        return $this->redirectToRoute('app_candidat_index', [], Response::HTTP_SEE_OTHER);


    }
    #[Route('/accepter/{id}', name: 'app_candidat_accepter',methods: ['GET', 'POST'])]
    public function accepter(Request $request, Candidat $candidat, candidatRepository $candidatRepository): Response

    {

        $candidat->setEtat("1");
        $candidatRepository->save($candidat, true);


        return $this->redirectToRoute('app_candidat_index', [], Response::HTTP_SEE_OTHER);


    }



}
