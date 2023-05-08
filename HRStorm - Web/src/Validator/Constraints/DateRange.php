<?php

namespace App\Validator\Constraints;

use Symfony\Component\Validator\Constraint;

/**
 * @Annotation
 */
class DateRange extends Constraint
{
    public $message = 'The end date must be greater than the start date and the difference between them must be at least a week.';
}
