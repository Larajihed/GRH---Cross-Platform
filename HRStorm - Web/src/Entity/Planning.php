<?php

namespace App\Entity;

use App\Repository\PlanningRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert ;
use Symfony\Component\Validator\Constraints\Callback;




//controle de saisie

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Context\ExecutionContextInterface;

#[ORM\Entity(repositoryClass: PlanningRepository::class)]
class Planning
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    #[Assert\NotNull(message:"Please enter a start date.")]
    #[Assert\NotBlank(message:"Please enter a start date.")]
    private ?\DateTime $dateDebut = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    #[Assert\NotNull(message:"Please enter an end date.")]
    #[Assert\NotBlank(message:"Please enter an end date.")]
    #[Callback([self::class, 'validateDateRange'])]
    private ?\DateTime $dateFin = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Le champ description est obligatoire")]
    #[Assert\Length(min:10)]
    private ?string $description = null;

    #[ORM\OneToMany(mappedBy: 'planning', targetEntity: Tache::class, orphanRemoval: true)]
    private Collection $taches;

    #[ORM\Column(length: 255)]
    #[Assert\Length(min:3)]
    private ?string $nom = null;

    public static function validateDateRange(?\DateTime $dateFin, ExecutionContextInterface $context): void
    {
        $dateDebut = $context->getObject()->getDateDebut();

        if ($dateFin !== null && $dateDebut !== null && $dateFin < $dateDebut) {
            $context->buildViolation('La date de fin doit etre superieur à la date de début ')
                ->atPath('dateFin')
                ->addViolation();
        }

        if ($dateFin !== null && $dateDebut !== null && $dateFin->diff($dateDebut)->days < 7) {
            $context->buildViolation('La difference entre les deux dates doit etre superieure ou égale à 7 jours ')
                ->atPath('dateFin')
                ->addViolation();
        }
    }

    public function __construct()
    {
        $this->taches = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDateDebut(): ?\DateTimeInterface
    {
        return $this->dateDebut;
    }

    public function setDateDebut(\DateTimeInterface $dateDebut): self
    {
        $this->dateDebut = $dateDebut;

        return $this;
    }

    public function getDateFin(): ?\DateTimeInterface
    {
        return $this->dateFin;
    }

    public function setDateFin(\DateTimeInterface $dateFin): self
    {
        $this->dateFin = $dateFin;

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

    /**
     * @return Collection<int, Tache>
     */
    public function getTaches(): Collection
    {
        return $this->taches;
    }

    public function addTach(Tache $tach): self
    {
        if (!$this->taches->contains($tach)) {
            $this->taches->add($tach);
            $tach->setPlanning($this);
        }

        return $this;
    }

    public function removeTach(Tache $tach): self
    {
        if ($this->taches->removeElement($tach)) {
            // set the owning side to null (unless already changed)
            if ($tach->getPlanning() === $this) {
                $tach->setPlanning(null);
            }
        }

        return $this;
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
}
