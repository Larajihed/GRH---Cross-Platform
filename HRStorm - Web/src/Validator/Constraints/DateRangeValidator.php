<?php

namespace App\Validator\Constraints;

use DateTime;
use Symfony\Component\Validator\Constraint;
use Symfony\Component\Validator\ConstraintValidator;
use Symfony\Component\Validator\Exception\UnexpectedTypeException;

class DateRangeValidator extends ConstraintValidator
{
    public function validate($value, Constraint $constraint)
    {
        if (!$constraint instanceof DateRange) {
            throw new UnexpectedTypeException($constraint, DateRange::class);
        }

        if ($value->diff($this->context->getObject()->getDateDebut())->days < 7) {
            $this->context->buildViolation($constraint->message)
                ->addViolation();
        }
    }
}
