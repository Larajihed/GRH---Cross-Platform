<?php

namespace App\Form;

use App\Entity\Budget;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Validator\Constraints\NotNull;
use Symfony\Component\Validator\Constraints\NotBlank;


class BudgetType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('budget')
            //->add('date')
            ->add('date', DateType::class, [
                'widget' => 'single_text',
                'input' => 'datetime',
                'format' => 'MM/yyyy',
                'html5' => false,
                'label' => 'Date',
                'attr' => ['placeholder' => 'MM/YYYY'],
                'invalid_message' => 'La date ne doit pas être nulle.',
                'constraints' => [
                    new NotNull(),
                ],
            ])

            ->add('prime')
            ->add('budget_materiel')
            ->add('budget_salaire')
            ->add('budget_service')

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Budget::class,
        ]);
    }
}
