<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;
use App\Repository\BudgetRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
use App\Entity\Depense;
use App\Repository\DepenseRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;


#[ORM\Entity(repositoryClass: BudgetRepository::class)]
/**
 * @Assert\Callback("validateSum")
 */
class Budget
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
     * @Assert\NotBlank(message="budget doit etre non vide")
     * @Assert\NotNull(message="budget doit etre non nul")
     * @Groups("post:read")
     */
    private ?float $budget = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    /**
     * @Assert\NotBlank(message="La date ne doit pas être vide.")
     */
    private ?\DateTimeInterface $date = null;

    #[ORM\Column(type: Types::FLOAT)]
    /**
     * @Assert\NotBlank(message="prime doit etre non vide")
     * @Groups("post:read")
     */
    private ?float $prime = null;

    #[ORM\Column]
    /**
     * @Assert\NotBlank(message="budget_materiel doit être non vide")
     * @Groups("post:read")
     */
    public ?float $budget_materiel = null;

    #[ORM\Column]
    /**
     * @Assert\NotBlank(message="budget_salaire doit être non vide")
     * @Groups("post:read")
     */
    public ?float $budget_salaire = null;

    #[ORM\Column]
    /**
     * @Assert\NotBlank(message="budget_service doit être non vide")
     * @Groups("post:read")
     */
    public ?float $budget_service = null;

    #[ORM\OneToMany(mappedBy: 'budget', targetEntity: Depense::class)]
    private Collection $depenses;



    /**
     * @Assert\Callback
     */
    public function validateBudgetTotal(ExecutionContextInterface $context)
    {
        $budgetTotal = $this->budget_materiel + $this->budget_salaire + $this->budget_service;

        if ($budgetTotal !== $this->budget) {
            $context->buildViolation('La somme des budgets ne correspond pas au budget total')
                ->atPath('budget_materiel')
                ->atPath('budget_salaire')
                ->atPath('budget_service')
                ->addViolation();
        }
    }

    public function __construct()
    {
        $this->depenses = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }



    public function setId(int $id): self
    {
        $this->id = $id;

        return $this;
    }
    public function getBudget(): ?float
    {
        return $this->budget;
    }



    public function setBudget(float $budget): self
    {
        $this->budget = $budget;

        return $this;
    }

    public function getDate(): ?\DateTime
    {
        return $this->date;
    }

    public function setDate(\DateTime $date): self
    {

        $this->date = $date;

        return $this;
    }

    public function getPrime(): ?float
    {
        return $this->prime;
    }

    public function setPrime(float $prime): self
    {
        $this->prime = $prime;

        return $this;
    }

    public function getBudget_Materiel(): ?float
    {
        return $this->budget_materiel;
    }
    public function getBudgetMateriel(): ?float
    {
        return $this->budget_materiel;
    }

    public function setBudget_Materiel(float $budget_materiel): self
    {
        $this->budget_materiel = $budget_materiel;

        return $this;
    }

    public function getBudget_Salaire(): ?float
    {
        return $this->budget_salaire;
    }
    public function getBudgetSalaire(): ?float
    {
        return $this->budget_salaire;
    }

    public function setBudget_Salaire(float $budget_salaire): self
    {
        $this->budget_salaire = $budget_salaire;

        return $this;
    }

    public function getBudget_Service(): ?float
    {
        return $this->budget_service;
    }
    public function getBudgetService(): ?float
    {
        return $this->budget_service;
    }
    public function setBudget_Service(float $budget_service): self
    {
        $this->budget_service = $budget_service;

        return $this;
    }

    public static function validateSum(Budget $budget, ExecutionContextInterface $context)
    {
        $sum = $budget->getBudgetMateriel() + $budget->getBudgetSalaire() + $budget->getBudgetService();
        $budgetValue = $budget->getBudget();

        if ($sum !== $budgetValue) {
            $context->buildViolation('La somme des attributs doit être égale à budget.')
                ->atPath('budget_materiel')
                ->addViolation();
            $context->buildViolation('La somme des attributs doit être égale à budget.')
                ->atPath('budget_salaire')
                ->addViolation();
            $context->buildViolation('La somme des attributs doit être égale à budget.')
                ->atPath('budget_service')
                ->addViolation();
        }
        }

    /**
     * @return Collection<int, Depense>
     */
    public function getDepenses(): Collection
    {
        return $this->depenses;
    }

    public function addDepense(Depense $depense): self
    {
        if (!$this->depenses->contains($depense)) {
            $this->depenses->add($depense);
            $depense->setBudget($this);
        }

        return $this;
    }

    public function removeDepense(Depense $depense): self
    {
        if ($this->depenses->removeElement($depense)) {
            // set the owning side to null (unless already changed)
            if ($depense->getBudget() === $this) {
                $depense->setBudget(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Depense>
     */


    public function __toString(): string
    {
        return (string)
        $this->id

            ;
    }


}
