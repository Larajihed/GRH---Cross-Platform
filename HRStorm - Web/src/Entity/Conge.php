<?php

namespace App\Entity;
use App\Entity\User;

use App\Repository\CongeRepository;
use Doctrine\DBAL\Types\Types;
use App\Repository\CompetenceRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use App\Entity\Niveau;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: CongeRepository::class)]
class Conge
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("conges")]

    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"catégorie obligatoire")]
    #[Groups("conges")]

    private ?string $categorie = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"description obligatoire")]
    #[Groups("conges")]

    private ?string $description = null;

    #[ORM\ManyToOne(cascade: ['persist', 'remove'])]
    #[ORM\JoinColumn(nullable: false)]
    private ?user $id_user = null;




    #[ORM\Column]
    #[Groups("conges")]

    private ?int $etat = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Groups("conges")]
    

    private ?\DateTimeInterface $debut = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Groups("conges")]
  

    private ?\DateTimeInterface $fin = null;

    #[ORM\Column(length: 255)]
    #[Groups("conges")]
   private ?string $image = null;

    

    // #[ORM\Column]
    // private ?\DateInterval $duree = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getCategorie(): ?string
    {
        return $this->categorie;
    }

    public function setCategorie(string $categorie): self
    {
        $this->categorie = $categorie;

        return $this;
    }
    public function __toString()
    {
        return (string)
        $this->categorie;
        $this->debut;
        $this->fin;

    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getIdUser(): ?user
    {
        return $this->id_user;
    }

    public function setIdUser(user $id_user): self
    {
        $this->id_user = $id_user;

        return $this;
    }

   
    public function getEtat(): ?int
    {
        return $this->etat;
    }

    public function setEtat(int $etat): self
    {
        $this->etat = $etat;

        return $this;
    }


    public function getDebut(): ?\DateTimeInterface
    {
        return $this->debut;
    }

    public function setDebut(\DateTimeInterface $debut): self
    {
        $this->debut = $debut;

        return $this;
    }

    public function getFin(): ?\DateTimeInterface
    {
        return $this->fin;
    }

    public function setFin(\DateTimeInterface $fin): self
    {
        $this->fin = $fin;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }

}
