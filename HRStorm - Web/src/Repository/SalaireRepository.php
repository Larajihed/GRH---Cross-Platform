<?php

namespace App\Repository;

use App\Entity\Salaire;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Salaire>
 *
 * @method Salaire|null find($id, $lockMode = null, $lockVersion = null)
 * @method Salaire|null findOneBy(array $criteria, array $orderBy = null)
 * @method Salaire[]    findAll()
 * @method Salaire[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class SalaireRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Salaire::class);
    }

    public function save(Salaire $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Salaire $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function findSalaire($searchTerm)
    {
        return $this->createQueryBuilder('s')
            ->where('s.date LIKE :searchTerm OR s.montant LIKE :searchTerm OR s.id_user LIKE :searchTerm ')
            ->setParameter('searchTerm', '%'.$searchTerm.'%')
            ->getQuery()
            ->getResult();
    }

//    /**
//     * @return Salaire[] Returns an array of Salaire objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('s')
//            ->andWhere('s.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('s.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Salaire
//    {
//        return $this->createQueryBuilder('s')
//            ->andWhere('s.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }

    public function findbyid($id_user) {
        $queryBuilder = $this->createQueryBuilder('s');

        if (!empty($id_user['title'])) {
            $queryBuilder->andWhere('s.title LIKE :title')
                ->setParameter('title', '%'.$id_user['title'].'%');
        }

        if (!empty($id_user['location'])) {
            $queryBuilder->andWhere('s.location LIKE :location')
                ->setParameter('location', '%'.$id_user['location'].'%');
        }

        // Ajoutez d'autres critères de recherche en fonction des propriétés de votre entité "salaire".

        return $queryBuilder->getQuery()->getResult();
    }

    public function order_By_date()
    {
        return $this->createQueryBuilder('s')
            ->orderBy('s.date', 'ASC')
            ->getQuery()->getResult();
    }
    public function order_By_montant()
    {
        return $this->createQueryBuilder('s')
            ->orderBy('s.montant', 'ASC')
            ->getQuery()->getResult();
    }

    public function searchid($id_user)
    {
        $EM=$this->getEntityManager();
        $query = $EM->createQuery('select s from App\Entity\Salaire s  WHERE s.id_user  BETWEEN :a AND :b ')
            ->setParameter('a', 0)
            ->setParameter('b', $id_user);
        return $query->getResult();


    }


    public function searchdate($date)
    {
        $EM=$this->getEntityManager();
        $query = $EM->createQuery('select s from App\Entity\Salaire s  WHERE s.date > :b ')
            ->setParameter('b', $date);
        return $query->getResult();
    }



}
