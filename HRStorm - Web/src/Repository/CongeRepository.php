<?php

namespace App\Repository;

use App\Entity\Conge;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Conge>
 *
 * @method Conge|null find($id, $lockMode = null, $lockVersion = null)
 * @method Conge|null findOneBy(array $criteria, array $orderBy = null)
 * @method Conge[]    findAll()
 * @method Conge[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class CongeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Conge::class);
    }

    public function save(Conge $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Conge $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function findConge($searchTerm)
    {
        return $this->createQueryBuilder('c')
            ->where('c.categorie LIKE :searchTerm OR c.description LIKE :searchTerm OR c.debut LIKE :searchTerm OR c.fin LIKE :searchTerm ')
            ->setParameter('searchTerm', '%'.$searchTerm.'%')
            ->getQuery()
            ->getResult();
    }
    public function countByEtat(): array
    {
        $qb = $this->createQueryBuilder('c')
            ->select('c.etat, COUNT(c.id) as count')
            ->groupBy('c.etat');
    
        $results = $qb->getQuery()->getResult();
    
        // Map the etat value to a human-readable label
        foreach ($results as &$row) {
            switch ($row['etat']) {
                case 0:
                    $row['etatLabel'] = 'En attente';
                    break;
                case 1:
                    $row['etatLabel'] = 'Accepté';
                    break;
                case 2:
                    $row['etatLabel'] = 'Refusé';
                    break;
                default:
                    $row['etatLabel'] = '';
            }
        }
    
        return $results;
    }
    


//    /**
//     * @return Conge[] Returns an array of Conge objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('c')
//            ->andWhere('c.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('c.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Conge
//    {
//        return $this->createQueryBuilder('c')
//            ->andWhere('c.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
