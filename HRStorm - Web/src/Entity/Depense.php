<?php

namespace App\Entity;
use App\Entity\Budget;
use App\Repository\DepenseRepository;
use App\Repository\BudgetRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\EntityManagerInterface;

#[ORM\Entity(repositoryClass: DepenseRepository::class)]

/**
 * @ORM\Entity(repositoryClass=DepenseRepository::class)
 * @ORM\Table(name="depense")
 */
class Depense
{


    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    # /**
        #  * @Assert\NotBlank
        #  */
    private ?int $id = null;

    #[ORM\Column]
    /**
     * @ORM\Column(length=255)
     * @Assert\NotBlank(message="le nom doit etre non vide")
     * @Groups("post:read")
     */
    private ?string $nom = null;

    #[ORM\Column]
    /**
     * @ORM\Column(type="float")
     * @Assert\NotBlank(message="le montant doit etre non vide")
     * @Assert\NotNull(message="le montant doit etre non nul")
     * @Groups("post:read")
     */
    private ?float $montant = null;


    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #  /**
        #  * @Assert\Date
        #  * @var date A "Y-m-d" formatted value
        #  */
    private ?\DateTimeInterface $date = null;

    #[ORM\Column]
    /**
     * @ORM\Column(length=255)
     * @Assert\NotBlank(message="le justificatif doit etre non vide")
     * @Groups("post:read")
     */
    private ?string $justificatif = null;

    #[ORM\Column]
    /**
     * @ORM\Column(length=255)
     */
    private ?string $categorie = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(nullable: false)]
    private ?Budget $id_budget = null;



    public function getId(): ?int
    {
        return $this->id;
    }



    public function __construct()
    {
    }


    public function __toString(): string
    {
        return (string) $this->nom;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getMontant(): ?float
    {
        return $this->montant;
    }

    public function setMontant(float $montant): self
    {
        $this->montant = $montant;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getJustificatif(): ?string
    {
        return $this->justificatif;
    }

    public function setJustificatif(string $justificatif): self
    {
        $this->justificatif = $justificatif;

        return $this;
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

    public function getIdBudget(): ?Budget
    {
        return $this->id_budget;
    }

    public function setIdBudget(?Budget $id_budget): self
    {
        $this->id_budget = $id_budget;

        return $this;
    }







}

