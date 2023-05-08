<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230307060716 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE budget (id INT AUTO_INCREMENT NOT NULL, budget DOUBLE PRECISION NOT NULL, date DATE NOT NULL, prime DOUBLE PRECISION NOT NULL, budget_materiel DOUBLE PRECISION NOT NULL, budget_salaire DOUBLE PRECISION NOT NULL, budget_service DOUBLE PRECISION NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE candidat (id INT AUTO_INCREMENT NOT NULL, idrecrutement_id INT NOT NULL, nom VARCHAR(255) NOT NULL, prenom VARCHAR(255) NOT NULL, datenaissance DATE NOT NULL, tel INT NOT NULL, email VARCHAR(255) NOT NULL, lettremotivation LONGTEXT NOT NULL, cv VARCHAR(255) NOT NULL, etat INT NOT NULL, INDEX IDX_6AB5B471EDBF8030 (idrecrutement_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE competence (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, description VARCHAR(255) DEFAULT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE conge (id INT AUTO_INCREMENT NOT NULL, id_user_id INT NOT NULL, categorie VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, etat INT NOT NULL, debut DATE NOT NULL, fin DATE NOT NULL, image VARCHAR(255) NOT NULL, INDEX IDX_2ED8934879F37AE5 (id_user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE conje (id INT AUTO_INCREMENT NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE depense (id INT AUTO_INCREMENT NOT NULL, id_budget_id INT NOT NULL, nom VARCHAR(255) NOT NULL, montant DOUBLE PRECISION NOT NULL, date DATE NOT NULL, justificatif VARCHAR(255) NOT NULL, categorie VARCHAR(255) NOT NULL, INDEX IDX_34059757B69E1A9F (id_budget_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE dompdf (id INT AUTO_INCREMENT NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE evaluation (id INT AUTO_INCREMENT NOT NULL, employee_id INT NOT NULL, poste_id INT NOT NULL, date DATE NOT NULL, commentaire VARCHAR(255) DEFAULT NULL, experience SMALLINT NOT NULL, level VARCHAR(20) NOT NULL, INDEX IDX_1323A5758C03F15C (employee_id), INDEX IDX_1323A575A0905086 (poste_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE evaluation_competence (evaluation_id INT NOT NULL, competence_id INT NOT NULL, INDEX IDX_7ED32F8D456C5646 (evaluation_id), INDEX IDX_7ED32F8D15761DAB (competence_id), PRIMARY KEY(evaluation_id, competence_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE planning (id INT AUTO_INCREMENT NOT NULL, date_debut DATETIME NOT NULL, date_fin DATETIME NOT NULL, description VARCHAR(255) NOT NULL, nom VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE poste (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, missions VARCHAR(600) NOT NULL, description VARCHAR(600) NOT NULL, salaire_max DOUBLE PRECISION NOT NULL, salaire_min DOUBLE PRECISION NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE poste_competence (poste_id INT NOT NULL, competence_id INT NOT NULL, INDEX IDX_E5C1CC4DA0905086 (poste_id), INDEX IDX_E5C1CC4D15761DAB (competence_id), PRIMARY KEY(poste_id, competence_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE question (id INT AUTO_INCREMENT NOT NULL, quiz_id INT NOT NULL, question VARCHAR(255) NOT NULL, options LONGTEXT NOT NULL COMMENT \'(DC2Type:json)\', correct_option INT NOT NULL, INDEX IDX_B6F7494E853CD175 (quiz_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE quiz (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE recrutement (id INT AUTO_INCREMENT NOT NULL, titre VARCHAR(255) NOT NULL, description VARCHAR(3000) NOT NULL, nbrposte INT NOT NULL, salaire DOUBLE PRECISION NOT NULL, type VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE salaire (id INT AUTO_INCREMENT NOT NULL, id_user INT NOT NULL, montant DOUBLE PRECISION NOT NULL, date DATE NOT NULL, taux_augmentation DOUBLE PRECISION NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE solde_conge (id INT AUTO_INCREMENT NOT NULL, id_user_id INT NOT NULL, solde INT NOT NULL, UNIQUE INDEX UNIQ_EF1BB2779F37AE5 (id_user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE tache (id INT AUTO_INCREMENT NOT NULL, planning_id INT NOT NULL, nom VARCHAR(255) NOT NULL, priorite VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, INDEX IDX_938720753D865311 (planning_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE user (id INT AUTO_INCREMENT NOT NULL, email VARCHAR(180) NOT NULL, roles LONGTEXT NOT NULL COMMENT \'(DC2Type:json)\', password VARCHAR(255) NOT NULL, is_verified TINYINT(1) NOT NULL, nom VARCHAR(255) NOT NULL, prenom VARCHAR(255) NOT NULL, nomsociete VARCHAR(255) DEFAULT NULL, UNIQUE INDEX UNIQ_8D93D649E7927C74 (email), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE messenger_messages (id BIGINT AUTO_INCREMENT NOT NULL, body LONGTEXT NOT NULL, headers LONGTEXT NOT NULL, queue_name VARCHAR(190) NOT NULL, created_at DATETIME NOT NULL, available_at DATETIME NOT NULL, delivered_at DATETIME DEFAULT NULL, INDEX IDX_75EA56E0FB7336F0 (queue_name), INDEX IDX_75EA56E0E3BD61CE (available_at), INDEX IDX_75EA56E016BA31DB (delivered_at), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE candidat ADD CONSTRAINT FK_6AB5B471EDBF8030 FOREIGN KEY (idrecrutement_id) REFERENCES recrutement (id)');
        $this->addSql('ALTER TABLE conge ADD CONSTRAINT FK_2ED8934879F37AE5 FOREIGN KEY (id_user_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE depense ADD CONSTRAINT FK_34059757B69E1A9F FOREIGN KEY (id_budget_id) REFERENCES budget (id)');
        $this->addSql('ALTER TABLE evaluation ADD CONSTRAINT FK_1323A5758C03F15C FOREIGN KEY (employee_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE evaluation ADD CONSTRAINT FK_1323A575A0905086 FOREIGN KEY (poste_id) REFERENCES poste (id)');
        $this->addSql('ALTER TABLE evaluation_competence ADD CONSTRAINT FK_7ED32F8D456C5646 FOREIGN KEY (evaluation_id) REFERENCES evaluation (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE evaluation_competence ADD CONSTRAINT FK_7ED32F8D15761DAB FOREIGN KEY (competence_id) REFERENCES competence (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE poste_competence ADD CONSTRAINT FK_E5C1CC4DA0905086 FOREIGN KEY (poste_id) REFERENCES poste (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE poste_competence ADD CONSTRAINT FK_E5C1CC4D15761DAB FOREIGN KEY (competence_id) REFERENCES competence (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE question ADD CONSTRAINT FK_B6F7494E853CD175 FOREIGN KEY (quiz_id) REFERENCES quiz (id)');
        $this->addSql('ALTER TABLE solde_conge ADD CONSTRAINT FK_EF1BB2779F37AE5 FOREIGN KEY (id_user_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE tache ADD CONSTRAINT FK_938720753D865311 FOREIGN KEY (planning_id) REFERENCES planning (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE candidat DROP FOREIGN KEY FK_6AB5B471EDBF8030');
        $this->addSql('ALTER TABLE conge DROP FOREIGN KEY FK_2ED8934879F37AE5');
        $this->addSql('ALTER TABLE depense DROP FOREIGN KEY FK_34059757B69E1A9F');
        $this->addSql('ALTER TABLE evaluation DROP FOREIGN KEY FK_1323A5758C03F15C');
        $this->addSql('ALTER TABLE evaluation DROP FOREIGN KEY FK_1323A575A0905086');
        $this->addSql('ALTER TABLE evaluation_competence DROP FOREIGN KEY FK_7ED32F8D456C5646');
        $this->addSql('ALTER TABLE evaluation_competence DROP FOREIGN KEY FK_7ED32F8D15761DAB');
        $this->addSql('ALTER TABLE poste_competence DROP FOREIGN KEY FK_E5C1CC4DA0905086');
        $this->addSql('ALTER TABLE poste_competence DROP FOREIGN KEY FK_E5C1CC4D15761DAB');
        $this->addSql('ALTER TABLE question DROP FOREIGN KEY FK_B6F7494E853CD175');
        $this->addSql('ALTER TABLE solde_conge DROP FOREIGN KEY FK_EF1BB2779F37AE5');
        $this->addSql('ALTER TABLE tache DROP FOREIGN KEY FK_938720753D865311');
        $this->addSql('DROP TABLE budget');
        $this->addSql('DROP TABLE candidat');
        $this->addSql('DROP TABLE competence');
        $this->addSql('DROP TABLE conge');
        $this->addSql('DROP TABLE conje');
        $this->addSql('DROP TABLE depense');
        $this->addSql('DROP TABLE dompdf');
        $this->addSql('DROP TABLE evaluation');
        $this->addSql('DROP TABLE evaluation_competence');
        $this->addSql('DROP TABLE planning');
        $this->addSql('DROP TABLE poste');
        $this->addSql('DROP TABLE poste_competence');
        $this->addSql('DROP TABLE question');
        $this->addSql('DROP TABLE quiz');
        $this->addSql('DROP TABLE recrutement');
        $this->addSql('DROP TABLE salaire');
        $this->addSql('DROP TABLE solde_conge');
        $this->addSql('DROP TABLE tache');
        $this->addSql('DROP TABLE user');
        $this->addSql('DROP TABLE messenger_messages');
    }
}
