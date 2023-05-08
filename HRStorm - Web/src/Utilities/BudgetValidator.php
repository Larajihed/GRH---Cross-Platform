<?php
use Symfony\Component\Validator\Constraint;
use Symfony\Component\Validator\ConstraintValidator;

class BudgetValidator extends ConstraintValidator
{
    public function validate($value, Constraint $constraint)
    {
        $sum = $value->getBudgetMateriel() + $value->getBudgetSalaire() + $value->getBudgetService();

        if ($sum !== $value->getBudget()) {
            $this->context->buildViolation($constraint->message)
                ->atPath('$budget_materiel')
                ->addViolation();
            $this->context->buildViolation($constraint->message)
                ->atPath('budget_salaire')
                ->addViolation();
            $this->context->buildViolation($constraint->message)
                ->atPath('budget_service')
                ->addViolation();
        }
    }
}
