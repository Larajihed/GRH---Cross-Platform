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

class CandidatType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom', TextFormType::class, [
                'constraints' => [
                    new Length([
                        'min' => 3,
                        'max' => 30,
                        'minMessage' => 'Le nom doit comporter au moins {{ limit }} caractères',
                        'maxMessage' => 'Le nom ne peut pas dépasser {{ limit }} caractères',
                    ]),
                ],
            ])
            ->add('prenom', TextFormType::class, [
                'constraints' => [
                    new Length([
                        'min' => 3,
                        'max' => 30,
                        'minMessage' => 'Le prenom doit comporter au moins {{ limit }} caractères',
                        'maxMessage' => 'Le prenom ne peut pas dépasser {{ limit }} caractères',
                    ]),
                ],
            ])
            ->add('datenaissance', TextFormType::class, [
                'constraints' => [
                    new Length([
                        'min' => 3,
                        'max' => 10,
                        'minMessage' => 'La datenaissance doit comporter au moins {{ limit }} caractères',
                        'maxMessage' => 'La datenaissance ne peut pas dépasser {{ limit }} caractères',
                    ]),
                ],
            ])
            ->add('tel', TextFormType::class, [
                'constraints' => [
                    new Length([
                        'min' => 4,
                        'max' => 5,
                        'minMessage' => 'Le tel doit comporter au moins {{ limit }} caractères',
                        'maxMessage' => 'Le tel ne peut pas dépasser {{ limit }} caractères',
                    ]),
                ],
            ])
            ->add('email', TextFormType::class, [
                'constraints' => [
                    new Length([
                        'min' => 3,
                        'max' => 30,
                        'minMessage' => 'Le email doit comporter au moins {{ limit }} caractères',
                        'maxMessage' => 'Le email ne peut pas dépasser {{ limit }} caractères',
                    ]),
                ],
            ])
            ->add('lettremotivation', TextFormType::class, [
                'constraints' => [
                    new Length([
                        'min' => 3,
                        'max' => 30,
                        'minMessage' => 'La lettremotivation doit comporter au moins {{ limit }} caractères',
                        'maxMessage' => 'La lettremotivation ne peut pas dépasser {{ limit }} caractères',
                    ]),
                ],
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            // Configure your form options here
        ]);
    }
}
