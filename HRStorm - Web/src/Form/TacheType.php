<?php

namespace App\Form;

use App\Entity\Planning;
use App\Entity\Tache;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\ResetType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class TacheType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom')
            ->add('priorite',ChoiceType::class, [
                'choices'  => [
                    'high' => 'high',
                    'medium' => 'medium',
                    'low' => 'low',
                ],
            ])
            ->add('description')
            ->add('planning', EntityType::class,[
            'class'=>Planning::class,
                'choice_label'=>'id',
            ])
            ->add('submit',SubmitType::class)
            ->add('cancel',ResetType::class)
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Tache::class,
        ]);
    }
}
