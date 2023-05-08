<?php

namespace App\Form;

use App\Entity\SoldeConge;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use App\Entity\User;
use Doctrine\ORM\EntityRepository;

class SoldeCongeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('solde')
            ->add('id_user', EntityType::class, [
                'class' => User::class,
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('u')
                        ->leftJoin('App\Entity\SoldeConge', 'sc', 'WITH', 'sc.id_user = u.id')
                        ->where('sc.id_user IS NULL');
                },
                'choice_label' => function ($user) {
                    return $user->getNom().' '.$user->getPrenom();
                },
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => SoldeConge::class,
        ]);
    }
}