<?php

namespace App\Event;

use App\Entity\Budget;
use Symfony\Contracts\EventDispatcher\Event;

class ListAllBudgetsEvent extends Event
{
    const LIST_ALL_Budget_EVENT = 'page';

    public function __construct(private int $nbBudget) {}

    public function getNbBudget(): int {
        return $this->nbBudget;
    }

}