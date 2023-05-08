<?php

namespace App\Form;

use App\Entity\Evaluation;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

class Evaluation1Type extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('Date')
            ->add('Commentaire')
            ->add('Experience')
            ->add('Level', ChoiceType::class, [
                'choices' => [
                    'Junior' => 'Junior',
                    'Mid-level' => 'Mid_level',
                    'Senior' => 'Senior',
                ],
            ])
            ->add('Employee')
            ->add('Competences')
            ->add('Poste')

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Evaluation::class,
        ]);
    }
}
