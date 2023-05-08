<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;


class DompdfController extends AbstractController
{
public function myAction(): Response
{
$dompdf = $this->get(Dompdf::class);
$dompdf->loadHtml('<h1>Hello world!</h1>');
$dompdf->setPaper('A4', 'landscape');
$dompdf->render();

return new Response($dompdf->output(), 200, [
'Content-Type' => 'application/pdf',
'Content-Disposition' => 'attachment; filename="document.pdf"',
]);
}
}