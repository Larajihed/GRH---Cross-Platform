<?php

namespace App\Repository;

use App\Entity\Recrutement;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Recrutement>
 *
 * @method Recrutement|null find($id, $lockMode = null, $lockVersion = null)
 * @method Recrutement|null findOneBy(array $criteria, array $orderBy = null)
 * @method Recrutement[]    findAll()
 * @method Recrutement[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class RecrutementRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Recrutement::class);
    }

    public function save(Recrutement $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function findRecrutement($searchTerm)
    {
        return $this->createQueryBuilder('r')
            ->leftJoin('r.candidats', 'c')
            ->where('r.titre LIKE :searchTerm OR r.description LIKE :searchTerm OR r.nbrposte LIKE :searchTerm OR r.salaire LIKE :searchTerm OR r.type LIKE :searchTerm')
           // ->orWhere(':searchTerm MEMBER OF r.candidats')
            ->setParameter('searchTerm', '%'.$searchTerm.'%')
            ->getQuery()
            ->getResult();
    }


    public function remove(Recrutement $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

//    /**
//     * @return Recrutement[] Returns an array of Recrutement objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('r')
//            ->andWhere('r.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('r.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Recrutement
//    {
//        return $this->createQueryBuilder('r')
//            ->andWhere('r.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
