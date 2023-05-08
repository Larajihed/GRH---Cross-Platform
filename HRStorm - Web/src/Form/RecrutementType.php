<?php

namespace App\Form;

use App\Entity\Recrutement;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use App\Entity\User;
use App\Form\EmailType;
use Symfony\Component\Form\Extension\Core\Type\EmailType as EmailFormType;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Validator\Constraints\IsTrue;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Form\Extension\Core\Type\TextType as TextFormType;

class RecrutementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titre', TextFormType::class, [
                'constraints' => [
                    new Length([
                        'min' => 3,
                        'max' => 30,
                        'minMessage' => 'Le titre doit comporter au moins {{ limit }} caractères',
                        'maxMessage' => 'Le titre ne peut pas dépasser {{ limit }} caractères',
                    ]),
                ],
            ])
            ->add('description', TextFormType::class, [
                'constraints' => [
                    new Length([
                        'min' => 3,
                        'max' => 30,
                        'minMessage' => 'La description doit comporter au moins {{ limit }} caractères',
                        'maxMessage' => 'La description ne peut pas dépasser {{ limit }} caractères',
                    ]),
                ],
            ])
            ->add('nbrposte', TextFormType::class, [
                'constraints' => [
                    new Length([
                        'min' => 3,
                        'max' => 10,
                        'minMessage' => 'Le nombre de poste doit comporter au moins {{ limit }} caractères',
                        'maxMessage' => 'Le nombre de poste ne peut pas dépasser {{ limit }} caractères',
                    ]),
                ],
            ])
            ->add('salaire', TextFormType::class, [
                'constraints' => [
                    new Length([
                        'min' => 4,
                        'max' => 5,
                        'minMessage' => 'Le salaire doit comporter au moins {{ limit }} caractères',
                        'maxMessage' => 'Le salaire ne peut pas dépasser {{ limit }} caractères',
                    ]),
                ],
            ])
            ->add('type', TextFormType::class, [
                'constraints' => [
                    new Length([
                        'min' => 3,
                        'max' => 30,
                        'minMessage' => 'Le type doit comporter au moins {{ limit }} caractères',
                        'maxMessage' => 'Le type ne peut pas dépasser {{ limit }} caractères',
                    ]),
                ],
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Recrutement::class,
        ]);
    }
}
