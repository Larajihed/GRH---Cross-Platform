<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;

class PdfController extends AbstractController
{
    public function pdf(): Response
{
    $dompdf = new Dompdf();
    $html = "<h1>Hello world</h1>";
    $dompdf->loadHtml($html);
    $dompdf->setPaper('A4', 'landscape');
    $dompdf->render();

    $pdfOutput = $dompdf->output();

    $response = new Response($pdfOutput);
    $response->headers->set('Content-Type', 'application/pdf');
    $response->headers->set('Content-Disposition', 'inline; filename="hello_world.pdf"');

    return $response;
}
}
