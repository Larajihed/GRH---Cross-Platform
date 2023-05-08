<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use BotMan\BotMan\BotMan;
use BotMan\BotMan\Drivers\DriverManager;
use BotMan\BotMan\BotManFactory;


class BotmanController extends AbstractController
{
    #[Route('/messagebot', name: 'messageBotMan')]
    function messageAction(Request $request)
    {

        // Configuration for the BotMan WebDriver
        $config = [];

        DriverManager::loadDriver(\BotMan\Drivers\Web\WebDriver::class);

        // Create BotMan instance
        $botman = BotManFactory::create($config);

        // Give the bot some things to listen for.
        $botman->hears('Salut', function (BotMan $bot) {
            $bot->reply('Je suis à votre disposition pour toute question et tout conseil.');
        });

        $botman->hears('(c\'est quoi un cv ?|cv)', function (BotMan $bot) {
            $bot->reply('Le curriculum vitæ est un document détaillant le parcours et les compétences acquises d\'un individu durant les dernières années..');
        });

        $botman->hears('(quel sont les types de contrat de travail ?|contrat)', function (BotMan $bot) {
            $bot->reply('Il existe principalement trois types de contrat : CDI, CDD et CVP.');
        });

        $botman->hears('(est ce que je peux travailler a distance ? ?|distance)', function (BotMan $bot) {
            $bot->reply('Oui nous offrons des possibilité de travail a distance.');
        });

        $botman->hears('(est ce que je peux négocier le salaire ?|salaire)', function (BotMan $bot) {
            $bot->reply('Oui c\'possible selon votre expérience et votre compétence.');
        });

        $botman->hears('Merci', function (BotMan $bot) {
            $bot->reply('Je suis à votre disposition pour toute question et tout conseil.');
        });


        // Set a fallback
        $botman->fallback(function (BotMan $bot) {
            $bot->reply('Désolé je n\'ai pas compris.');
        });

        // Start listening
        $botman->listen();

        return new Response();
    }

    #[Route('/messaagebot', name: 'homepage')]
    public function indexAction(Request $request)
    {
        return $this->render('botman/homepage.html.twig');
    }


    #[Route('/chatframebot', name: 'chatframe')]
    public function chatframeAction(Request $request)
    {
        return $this->render('botman/chat_frame.html.twig');
    }
}
