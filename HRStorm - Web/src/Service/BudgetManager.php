<?php

namespace App\Service;

use App\Entity\Budget;
use App\Entity\Depense;
use Doctrine\ORM\EntityManagerInterface;

class BudgetManager
{
    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }

    public function soustraireBudget(Depense $depense)
    {
        $budgetRepository = $this->entityManager->getRepository(Budget::class);
        $budget = $budgetRepository->findOneBy([]);
        if (!$budget) {
            throw new \Exception('Aucun objet Budget n\'a été trouvé en base de données.');
        }

        switch ($depense->getCategorie()) {
            case 'budget_salaire':
                $budget->setBudget_Salaire($budget->getBudget_Salaire() - $depense->getMontant());
                break;
            case 'budget_materiel':
                $budget->setBudget_Materiel($budget->getBudget_Materiel() - $depense->getMontant());
                break;
            case 'budget_service':
                $budget->setBudget_Service($budget->getBudget_Service() - $depense->getMontant());
                break;
            default:
                throw new \InvalidArgumentException('La catégorie fournie est invalide.');
        }

        $budget->setBudget($budget->getBudget() - $depense->getMontant());

        $this->entityManager->flush();
    }
}
