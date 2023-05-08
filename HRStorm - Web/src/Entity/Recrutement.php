<?php

namespace App\Entity;

use App\Repository\RecrutementRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\CongeRepository;
use Doctrine\DBAL\Types\Types;
use App\Repository\CompetenceRepository;
use App\Entity\Niveau;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: RecrutementRepository::class)]
class Recrutement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"titre obligatoire")]
    private ?string $titre = null;

    #[ORM\Column(length: 3000)]
    #[Assert\NotBlank(message:"description obligatoire")]
    private ?string $description = null;

    #[ORM\Column]
    #[Assert\NotBlank(message:"nbrposte obligatoire")]
    private ?int $nbrposte = null;

    #[ORM\Column]
    #[Assert\NotBlank(message:"salaire obligatoire")]
    private ?float $salaire = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"type obligatoire")]
    private ?string $type = null;

    #[ORM\OneToMany(mappedBy: 'idrecrutement', targetEntity: Candidat::class)]
    private Collection $candidats;

    public function __construct()
    {
        $this->candidats = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }


    public function setTitre(string $titre): self
    {
        $this->titre = $titre;

        return $this;
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

    public function __toString()
    {
        return (string) $this->titre;
    }

    public function getNbrposte(): ?int
    {
        return $this->nbrposte;
    }

    public function setNbrposte(int $nbrposte): self
    {
        $this->nbrposte = $nbrposte;

        return $this;
    }

    public function getSalaire(): ?float
    {
        return $this->salaire;
    }

    public function setSalaire(float $salaire): self
    {
        $this->salaire = $salaire;

        return $this;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): self
    {
        $this->type = $type;

        return $this;
    }

    /**
     * @return Collection<int, Candidat>
     */
    public function getCandidats(): Collection
    {
        return $this->candidats;
    }

    public function addCandidat(Candidat $candidat): self
    {
        if (!$this->candidats->contains($candidat)) {
            $this->candidats->add($candidat);
            $candidat->setIdrecrutement($this);
        }

        return $this;
    }

    public function removeCandidat(Candidat $candidat): self
    {
        if ($this->candidats->removeElement($candidat)) {
            // set the owning side to null (unless already changed)
            if ($candidat->getIdrecrutement() === $this) {
                $candidat->setIdrecrutement(null);
            }
        }

        return $this;
    }
}
