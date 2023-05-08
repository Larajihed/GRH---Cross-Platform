<?php

namespace App\Entity;

use App\Repository\PosteRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: PosteRepository::class)]
class Poste
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[Assert\NotBlank(message:"Nom de poste obligatoire")]
    #[Assert\Length(max:255, maxMessage: "Le nom de poste doit contenir au plus {{ limit }} caractères")]
    #[ORM\Column(length: 255)]
    private ?string $Nom = null;

    #[ORM\Column(length: 600)]
    #[Assert\Length(max:600, maxMessage: "Les missions doivent contenir au plus {{ limit }} caractères")]
    #[Assert\NotBlank(message:"Liste des missions obligatoire")]
    private ?string $Missions = null;

    #[Assert\Length(max:600, maxMessage: "La description doit contenir au plus {{ limit }} caractères")]
    #[Assert\NotBlank(message:"Description Obligatoire obligatoire")]
    #[ORM\Column(length: 600)]
    private ?string $Description = null;

    #[ORM\ManyToMany(targetEntity: Competence::class, inversedBy: 'postes')]
    private Collection $Competences;

    #[Assert\NotNull(message:"Le salaire max est obligatoire")]
    #[Assert\GreaterThan(value:0, message:"Le salaire max doit être positif")]
    #[ORM\Column(type: 'float')]
    private ?float $SALAIRE_MAX = null;

    #[Assert\NotNull(message:"Le salaire min est obligatoire")]
    #[Assert\GreaterThan(value:0, message:"Le salaire min doit être positif")]
    #[Assert\LessThanOrEqual(propertyPath: "SALAIRE_MAX", message: "Le salaire min doit être inférieur ou égal au salaire max")]
    #[ORM\Column(type: 'float')]
    private ?float $SALAIRE_MIN = null;

    #[ORM\OneToMany(mappedBy: 'Poste', targetEntity: Evaluation::class, orphanRemoval: true)]
    private Collection $evaluations;
    public function __construct()
    {
        $this->Competences = new ArrayCollection();
        $this->evaluations = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->Nom;
    }

    public function setNom(?string $Nom): self
    {
        $this->Nom = $Nom;

        return $this;
    }

    public function getMissions(): ?string
    {
        return $this->Missions;
    }

    public function setMissions(?string $Missions): self
    {
        $this->Missions = $Missions;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->Description;
    }

    public function setDescription(?string $Description): self
    {
        $this->Description = $Description;

        return $this;
    }

    /**
     * @return Collection<int, Competence>
     */
    public function getCompetences(): Collection
    {
        return $this->Competences;
    }

    public function addCompetence(?Competence $competence): self
    {
        if (!$this->Competences->contains($competence)) {
            $this->Competences->add($competence);
        }

        return $this;
    }

    public function removeCompetence(?Competence $competence): self
    {
        $this->Competences->removeElement($competence);

        return $this;
    }

    public function getSALAIREMAX(): ?float
    {
        return $this->SALAIRE_MAX;
    }

    public function setSALAIREMAX(?float $SALAIRE_MAX): self
    {
        $this->SALAIRE_MAX = $SALAIRE_MAX;

        return $this;
    }

    public function getSALAIREMIN(): ?float
    {
        return $this->SALAIRE_MIN;
    }

    public function setSALAIREMIN(?float $SALAIRE_MIN): self
    {
        $this->SALAIRE_MIN = $SALAIRE_MIN;

        return $this;
    }




    /**
     * @return Collection<int, Evaluation>
     */
    public function getEvaluations(): Collection
    {
        return $this->evaluations;
    }

    public function addEvaluation(Evaluation $evaluation): self
    {
        if (!$this->evaluations->contains($evaluation)) {
            $this->evaluations->add($evaluation);
            $evaluation->setPoste($this);
        }

        return $this;
    }

    public function removeEvaluation(Evaluation $evaluation): self
    {
        if ($this->evaluations->removeElement($evaluation)) {
            // set the owning side to null (unless already changed)
            if ($evaluation->getPoste() === $this) {
                $evaluation->setPoste(null);
            }
        }

        return $this;
    }


    public function __toString() {
        return $this->Nom;
    }

}
