<?php

namespace App\Entity;

use App\Repository\EvaluationRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: EvaluationRepository::class)]
class Evaluation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Date = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $Commentaire = null;



    #[ORM\ManyToOne]
    #[ORM\JoinColumn(nullable: false)]
    #[Assert\NotBlank(message:"Employée Obligatoire")]
    private ?User $Employee = null;

   

    #[ORM\Column(type: Types::SMALLINT)]
    #[Assert\NotNull(message:"Expérience Obligatoire")]
    #[Assert\PositiveOrZero(message:"L'expérience doit être un entier positif ou nul")]
    private ?int $Experience = null;

    #[ORM\Column(length: 20)]
    #[Assert\NotBlank(message:"Niveau Obligatoire")]
    private ?string $Level = null;

    #[ORM\ManyToMany(targetEntity: Competence::class, inversedBy: 'evaluations')]
    private Collection $Competences;

    #[ORM\ManyToOne(inversedBy: 'evaluations')]
    #[ORM\JoinColumn(nullable: false)]
    private ?Poste $Poste = null;

    public function __construct()
    {
        $this->Competences = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->Date;
    }

    public function setDate(?\DateTimeInterface $Date): self
    {
        $this->Date = $Date;

        return $this;
    }

    public function getCommentaire(): ?string
    {
        return $this->Commentaire;
    }

    public function setCommentaire(?string $Commentaire): self
    {
        $this->Commentaire = $Commentaire;

        return $this;
    }

    public function getEmployee(): ?User
    {
        return $this->Employee;
    }

    public function setEmployee(?User $Employee): self
    {
        $this->Employee = $Employee;

        return $this;
    }

    public function getExperience(): ?int
    {
        return $this->Experience;
    }

    public function setExperience(?int $Experience): self
    {
        $this->Experience = $Experience;

        return $this;
    }

    public function getLevel(): ?string
    {
        return $this->Level;
    }

    public function setLevel(?string $Level): self
    {
        $this->Level = $Level;

        return $this;
    }

    /**
     * @return Collection<int, competence>
     */
    public function getCompetences(): Collection
    {
        return $this->Competences;
    }

    public function addCompetence(competence $competence): self
    {
        if (!$this->Competences->contains($competence)) {
            $this->Competences->add($competence);
        }

        return $this;
    }

    public function removeCompetence(competence $competence): self
    {
        $this->Competences->removeElement($competence);

        return $this;
    }

    public function getPoste(): ?Poste
    {
        return $this->Poste;
    }

    public function setPoste(?Poste $Poste): self
    {
        $this->Poste = $Poste;

        return $this;
    }
    public function getMissingCompetences(): Collection
    {
        $posteCompetences = $this->getPoste()->getCompetences();
        $selectedCompetences = $this->getCompetences();

        return $posteCompetences->filter(function (Competence $competence) use ($selectedCompetences) {
            return !$selectedCompetences->contains($competence);
        });
    }

   
}
