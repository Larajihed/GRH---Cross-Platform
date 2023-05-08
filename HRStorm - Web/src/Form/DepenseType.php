<?php

namespace App\Form;

use App\Entity\Depense;
use Doctrine\DBAL\Types\DateTimeType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormTypeInterface;


class DepenseType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom')
            ->add('montant')
            ->add('date')
            ->add('justificatif')
            ->add('id_budget')

            ->add('categorie', ChoiceType::class, [
                'choices' => [
                    'budget_materiel' => 'budget_materiel',
                    'budget_salaire' => 'budget_salaire',
                    'budget_service' => 'budget_service',
                ],
            ])

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Depense::class,
        ]);
    }
}
