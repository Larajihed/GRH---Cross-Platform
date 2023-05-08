<?php

namespace App\Entity;

use App\Repository\DompdfRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: DompdfRepository::class)]
class Dompdf
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    public function getId(): ?int
    {
        return $this->id;
    }
}
