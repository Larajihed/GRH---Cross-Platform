<?php

namespace App\Entity;

use App\Repository\MyOptionsRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=MyOptionsRepository::class)
 */
class MyOptions
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank
     */
    private $defaultFont;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDefaultFont(): ?string
    {
        return $this->defaultFont;
    }

    public function setDefaultFont(string $defaultFont): self
    {
        $this->defaultFont = $defaultFont;

        return $this;
    }
}