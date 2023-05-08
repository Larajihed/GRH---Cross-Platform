<?php

namespace App\Form;

use App\Entity\Salaire;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\SearchType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;

class SearchSalaireType extends AbstractType
{

        public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('id_user', NumberType::class, [
                'label' => false,
                'attr' => ['class' => 'form_control',
                            'placeholder' => 'Entrer un id']

            ])
            ->add('submit', SubmitType::class, [
                'attr' => ['class' => 'form_control']
            ]);
    }
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Salaire::class,
            'method' => 'GET',
        ]);
    }

}



