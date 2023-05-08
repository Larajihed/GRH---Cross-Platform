<?php

namespace App\Form;

use App\Entity\Conge;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use App\Entity\User;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
use Symfony\Component\Validator\Constraints\Callback;
use Symfony\Component\Validator\Constraints\GreaterThan;
use Symfony\Component\Validator\Constraints\GreaterThanOrEqual;

use App\Form\EmailType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Form\Extension\Core\Type\EmailType as EmailFormType;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Validator\Constraints\IsTrue;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Form\Extension\Core\Type\TextType as TextFormType;

class CongeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('categorie', TextFormType::class, [
            'constraints' => [
                new Length([
                    'min' => 3,
                    'max' => 30,
                    'minMessage' => 'Le nom doit comporter au moins {{ limit }} caractères',
                    'maxMessage' => 'Le nom ne peut pas dépasser {{ limit }} caractères',
                ]),
            ],
        ])            
        ->add('description', TextFormType::class, [
            'constraints' => [
                new Length([
                    'min' => 3,
                    'max' => 255,
                    'minMessage' => 'La description doit comporter au moins {{ limit }} caractères',
                    'maxMessage' => 'La descriptionne peut pas dépasser {{ limit }} caractères',
                ]),
            ],
        ])
            ->add('debut', DateType::class, [
                'widget' => 'single_text',
                'label' => 'Date',
                'constraints' => [
                    new Callback([$this, 'validateDateRange']),
                    new GreaterThanOrEqual([
                        'value' => 'today',
                        'message' => 'La date de début doit être au moins aujourd\'hui.',
                    ]),
                ],
            ])
            ->add('fin', DateType::class, [
                'widget' => 'single_text',
                'label' => 'Date',
            ])
            ->add('image', FileType::class, [
                'label' => 'Brochure (PDF file)',

                // unmapped means that this field is not associated to any entity property
                'mapped' => false,

                // make it optional so you don't have to re-upload the PDF file
                // every time you edit the Product details
                'required' => false,

                // unmapped fields can't define their validation using annotations
                // in the associated entity, so you can use the PHP constraint classes
                'constraints' => [
                    new File([
                        'maxSize' => '1024k',
                        'mimeTypes' => [
                            'image/pdf',
                            'image/png',
                            'image/jpeg',
                            'image/jpg',
                            'image/jiff',
                            'image/gif',


                        ],
                        'mimeTypesMessage' => 'Please upload a valid PDF document',
                    ])
                ],
            ])
           
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Conge::class,
        ]);
    }

    public function validateDateRange($value, ExecutionContextInterface $context)
    {
        $form = $context->getRoot();
        $debut = $form->get('debut')->getData();
        $fin = $form->get('fin')->getData();

        if ($debut && $fin && $debut > $fin ) {
            $context->buildViolation('La date de début doit être supérieur à la date de fin.')
                ->addViolation();
        }
    }

}