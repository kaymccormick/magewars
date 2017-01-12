--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: appuser; Type: TABLE; Schema: public; Owner: springdev; Tablespace: 
--

CREATE TABLE appuser (
    userid integer NOT NULL,
    username character varying(255),
    password character varying(255)
);


ALTER TABLE public.appuser OWNER TO springdev;

--
-- Name: arenaobject; Type: TABLE; Schema: public; Owner: springdev; Tablespace: 
--

CREATE TABLE arenaobject (
    arenaobjectid integer NOT NULL,
    player integer NOT NULL,
    card_cardid integer,
    col integer,
    gameid integer,
    "row" integer
);


ALTER TABLE public.arenaobject OWNER TO springdev;

--
-- Name: card; Type: TABLE; Schema: public; Owner: springdev; Tablespace: 
--

CREATE TABLE card (
    cardid integer NOT NULL,
    cardenumname character varying(255),
    cardname character varying(255),
    disc character varying(31)
);


ALTER TABLE public.card OWNER TO springdev;

--
-- Name: card_cardid_seq; Type: SEQUENCE; Schema: public; Owner: springdev
--

CREATE SEQUENCE card_cardid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.card_cardid_seq OWNER TO springdev;

--
-- Name: game; Type: TABLE; Schema: public; Owner: springdev; Tablespace: 
--

CREATE TABLE game (
    gameid integer NOT NULL,
    name character varying(255),
    numplayers integer NOT NULL,
    status integer,
    userid integer NOT NULL
);


ALTER TABLE public.game OWNER TO springdev;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: springdev
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO springdev;

--
-- Name: mage_mageid_seq; Type: SEQUENCE; Schema: public; Owner: springdev
--

CREATE SEQUENCE mage_mageid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mage_mageid_seq OWNER TO springdev;

--
-- Name: player; Type: TABLE; Schema: public; Owner: springdev; Tablespace: 
--

CREATE TABLE player (
    playerslot integer NOT NULL,
    gameid integer NOT NULL,
    mageid integer NOT NULL,
    userid integer
);


ALTER TABLE public.player OWNER TO springdev;

--
-- Name: spellbook; Type: TABLE; Schema: public; Owner: springdev; Tablespace: 
--

CREATE TABLE spellbook (
    spellbookid integer NOT NULL,
    spellbookname character varying(255),
    createdbyuser_userid integer
);


ALTER TABLE public.spellbook OWNER TO springdev;

--
-- Name: spellbook_spellbookid_seq; Type: SEQUENCE; Schema: public; Owner: springdev
--

CREATE SEQUENCE spellbook_spellbookid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.spellbook_spellbookid_seq OWNER TO springdev;

--
-- Name: spellbookcard; Type: TABLE; Schema: public; Owner: springdev; Tablespace: 
--

CREATE TABLE spellbookcard (
    spellbook integer NOT NULL,
    copies integer,
    card integer NOT NULL
);


ALTER TABLE public.spellbookcard OWNER TO springdev;

--
-- Name: user_userid_seq; Type: SEQUENCE; Schema: public; Owner: springdev
--

CREATE SEQUENCE user_userid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_userid_seq OWNER TO springdev;

--
-- Name: userconnection; Type: TABLE; Schema: public; Owner: springdev; Tablespace: 
--

CREATE TABLE userconnection (
    providerid character varying(255) NOT NULL,
    provideruserid character varying(255) NOT NULL,
    accesstoken character varying(255),
    displayname character varying(255),
    expiretime numeric(19,2),
    imageurl character varying(255),
    profileurl character varying(255),
    rank integer NOT NULL,
    refreshtoken character varying(255),
    secret character varying(255),
    userid character varying(255)
);


ALTER TABLE public.userconnection OWNER TO springdev;

--
-- Name: zone; Type: TABLE; Schema: public; Owner: springdev; Tablespace: 
--

CREATE TABLE zone (
    col integer NOT NULL,
    "row" integer NOT NULL,
    gameid integer NOT NULL
);


ALTER TABLE public.zone OWNER TO springdev;

--
-- Data for Name: appuser; Type: TABLE DATA; Schema: public; Owner: springdev
--

COPY appuser (userid, username, password) FROM stdin;
37	testuser1	\N
38	testuser2	\N
39	george	piss
40	kay	poop
\.


--
-- Data for Name: arenaobject; Type: TABLE DATA; Schema: public; Owner: springdev
--

COPY arenaobject (arenaobjectid, player, card_cardid, col, gameid, "row") FROM stdin;
799	0	989	3	798	1
800	0	985	2	798	0
801	0	979	0	798	0
802	0	980	0	798	1
803	0	987	2	798	2
804	0	986	2	798	1
805	0	982	1	798	0
806	0	990	3	798	2
807	0	981	0	798	2
808	0	988	3	798	0
809	0	984	1	798	2
810	0	983	1	798	1
\.


--
-- Data for Name: card; Type: TABLE DATA; Schema: public; Owner: springdev
--

COPY card (cardid, cardenumname, cardname, disc) FROM stdin;
979	ADRAMELECH_LORD_OF_FIRE	Adramelech, Lord of Fire	CREATURE
980	BITTERWOOD_FOX	Bitterwood Fox	CREATURE
981	BLUE_GREMLIN	Blue Gremlin	CREATURE
982	BROGAN_BLOODSTONE	Brogan Bloodstone	CREATURE
983	CERVERE_THE_FOREST_SHADOW	Cervere, The Forest Shadow	CREATURE
984	DARK_PACT_SLAYER	Dark Pact Slayer	CREATURE
985	DARKFENNE_BAT	Darkfenne Bat	CREATURE
986	DARKFENNE_HYDRA	Darkfenne Hydra	CREATURE
987	DWARF_KRIEGSBIEL	Dwarf Kriegsbiel	CREATURE
988	GOBLIN_BUILDER	Goblin Builder	CREATURE
989	AGONY	Agony	ENCHANTMENT
990	BEAR_STRENGTH	Bear Strength	ENCHANTMENT
991	BLOCK	Block	ENCHANTMENT
992	BULL_ENDURANCE	Bull Endurance	ENCHANTMENT
993	CHAINS_OF_AGONY	Chains of Agony	ENCHANTMENT
994	CHARM	Charm	ENCHANTMENT
995	CHEETAH_SPEED	Cheetah Speed	ENCHANTMENT
996	CIRCLE_OF_FIRE	Circle of Fire	ENCHANTMENT
997	COBRA_REFLEXES	Cobra Reflexes	ENCHANTMENT
998	BEASTMASTER	BeastMaster	MAGE
999	WARLOCK	Warlock	MAGE
\.


--
-- Name: card_cardid_seq; Type: SEQUENCE SET; Schema: public; Owner: springdev
--

SELECT pg_catalog.setval('card_cardid_seq', 999, true);


--
-- Data for Name: game; Type: TABLE DATA; Schema: public; Owner: springdev
--

COPY game (gameid, name, numplayers, status, userid) FROM stdin;
798	Game created by testGame method.	2	1	37
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: springdev
--

SELECT pg_catalog.setval('hibernate_sequence', 810, true);


--
-- Name: mage_mageid_seq; Type: SEQUENCE SET; Schema: public; Owner: springdev
--

SELECT pg_catalog.setval('mage_mageid_seq', 3, true);


--
-- Data for Name: player; Type: TABLE DATA; Schema: public; Owner: springdev
--

COPY player (playerslot, gameid, mageid, userid) FROM stdin;
0	798	998	37
1	798	999	38
\.


--
-- Data for Name: spellbook; Type: TABLE DATA; Schema: public; Owner: springdev
--

COPY spellbook (spellbookid, spellbookname, createdbyuser_userid) FROM stdin;
49	\N	\N
\.


--
-- Name: spellbook_spellbookid_seq; Type: SEQUENCE SET; Schema: public; Owner: springdev
--

SELECT pg_catalog.setval('spellbook_spellbookid_seq', 49, true);


--
-- Data for Name: spellbookcard; Type: TABLE DATA; Schema: public; Owner: springdev
--

COPY spellbookcard (spellbook, copies, card) FROM stdin;
49	3	986
49	3	980
49	2	985
49	1	982
49	2	984
49	1	987
49	1	981
49	3	983
49	2	988
49	3	979
\.


--
-- Name: user_userid_seq; Type: SEQUENCE SET; Schema: public; Owner: springdev
--

SELECT pg_catalog.setval('user_userid_seq', 40, true);


--
-- Data for Name: userconnection; Type: TABLE DATA; Schema: public; Owner: springdev
--

COPY userconnection (providerid, provideruserid, accesstoken, displayname, expiretime, imageurl, profileurl, rank, refreshtoken, secret, userid) FROM stdin;
\.


--
-- Data for Name: zone; Type: TABLE DATA; Schema: public; Owner: springdev
--

COPY zone (col, "row", gameid) FROM stdin;
3	1	798
2	0	798
0	0	798
0	1	798
2	2	798
2	1	798
1	0	798
3	2	798
0	2	798
3	0	798
1	2	798
1	1	798
\.


--
-- Name: appuser_pkey; Type: CONSTRAINT; Schema: public; Owner: springdev; Tablespace: 
--

ALTER TABLE ONLY appuser
    ADD CONSTRAINT appuser_pkey PRIMARY KEY (userid);


--
-- Name: arenaobject_pkey; Type: CONSTRAINT; Schema: public; Owner: springdev; Tablespace: 
--

ALTER TABLE ONLY arenaobject
    ADD CONSTRAINT arenaobject_pkey PRIMARY KEY (arenaobjectid);


--
-- Name: card_pkey; Type: CONSTRAINT; Schema: public; Owner: springdev; Tablespace: 
--

ALTER TABLE ONLY card
    ADD CONSTRAINT card_pkey PRIMARY KEY (cardid);


--
-- Name: game_pkey; Type: CONSTRAINT; Schema: public; Owner: springdev; Tablespace: 
--

ALTER TABLE ONLY game
    ADD CONSTRAINT game_pkey PRIMARY KEY (gameid);


--
-- Name: player_pkey; Type: CONSTRAINT; Schema: public; Owner: springdev; Tablespace: 
--

ALTER TABLE ONLY player
    ADD CONSTRAINT player_pkey PRIMARY KEY (gameid, playerslot);


--
-- Name: spellbook_pkey; Type: CONSTRAINT; Schema: public; Owner: springdev; Tablespace: 
--

ALTER TABLE ONLY spellbook
    ADD CONSTRAINT spellbook_pkey PRIMARY KEY (spellbookid);


--
-- Name: spellbookcard_pkey; Type: CONSTRAINT; Schema: public; Owner: springdev; Tablespace: 
--

ALTER TABLE ONLY spellbookcard
    ADD CONSTRAINT spellbookcard_pkey PRIMARY KEY (spellbook, card);


--
-- Name: uk_418sr20kh207kb22viuyno1; Type: CONSTRAINT; Schema: public; Owner: springdev; Tablespace: 
--

ALTER TABLE ONLY appuser
    ADD CONSTRAINT uk_418sr20kh207kb22viuyno1 UNIQUE (username);


--
-- Name: uk_d4v2elghj4qva14w9ms9jq3bv; Type: CONSTRAINT; Schema: public; Owner: springdev; Tablespace: 
--

ALTER TABLE ONLY card
    ADD CONSTRAINT uk_d4v2elghj4qva14w9ms9jq3bv UNIQUE (cardenumname);


--
-- Name: userconnection_pkey; Type: CONSTRAINT; Schema: public; Owner: springdev; Tablespace: 
--

ALTER TABLE ONLY userconnection
    ADD CONSTRAINT userconnection_pkey PRIMARY KEY (providerid, provideruserid);


--
-- Name: zone_pkey; Type: CONSTRAINT; Schema: public; Owner: springdev; Tablespace: 
--

ALTER TABLE ONLY zone
    ADD CONSTRAINT zone_pkey PRIMARY KEY (col, gameid, "row");


--
-- Name: fk_2lbhk32xwomyv3mfla4xn7c1d; Type: FK CONSTRAINT; Schema: public; Owner: springdev
--

ALTER TABLE ONLY arenaobject
    ADD CONSTRAINT fk_2lbhk32xwomyv3mfla4xn7c1d FOREIGN KEY (col, gameid, "row") REFERENCES zone(col, gameid, "row");


--
-- Name: fk_2tobj270icn5k6vxrly1kxx6h; Type: FK CONSTRAINT; Schema: public; Owner: springdev
--

ALTER TABLE ONLY game
    ADD CONSTRAINT fk_2tobj270icn5k6vxrly1kxx6h FOREIGN KEY (userid) REFERENCES appuser(userid);


--
-- Name: fk_482qfo470o3v0yv6y3lox19e6; Type: FK CONSTRAINT; Schema: public; Owner: springdev
--

ALTER TABLE ONLY zone
    ADD CONSTRAINT fk_482qfo470o3v0yv6y3lox19e6 FOREIGN KEY (gameid) REFERENCES game(gameid);


--
-- Name: fk_9qoo0agsppytixiuge6e8rmcp; Type: FK CONSTRAINT; Schema: public; Owner: springdev
--

ALTER TABLE ONLY spellbookcard
    ADD CONSTRAINT fk_9qoo0agsppytixiuge6e8rmcp FOREIGN KEY (card) REFERENCES card(cardid);


--
-- Name: fk_a2qrrxci666vl51su0bu1am8v; Type: FK CONSTRAINT; Schema: public; Owner: springdev
--

ALTER TABLE ONLY arenaobject
    ADD CONSTRAINT fk_a2qrrxci666vl51su0bu1am8v FOREIGN KEY (card_cardid) REFERENCES card(cardid);


--
-- Name: fk_cdm604oyowcuwu69nm36kcmm; Type: FK CONSTRAINT; Schema: public; Owner: springdev
--

ALTER TABLE ONLY player
    ADD CONSTRAINT fk_cdm604oyowcuwu69nm36kcmm FOREIGN KEY (gameid) REFERENCES game(gameid);


--
-- Name: fk_e2fdxs9v2t62ks5xe39g3btwv; Type: FK CONSTRAINT; Schema: public; Owner: springdev
--

ALTER TABLE ONLY player
    ADD CONSTRAINT fk_e2fdxs9v2t62ks5xe39g3btwv FOREIGN KEY (userid) REFERENCES appuser(userid);


--
-- Name: fk_f1xkflkryxqp6rs4j78s9w3pt; Type: FK CONSTRAINT; Schema: public; Owner: springdev
--

ALTER TABLE ONLY userconnection
    ADD CONSTRAINT fk_f1xkflkryxqp6rs4j78s9w3pt FOREIGN KEY (userid) REFERENCES appuser(username);


--
-- Name: fk_fqc1mrjj8qf55e69kw7rcuva0; Type: FK CONSTRAINT; Schema: public; Owner: springdev
--

ALTER TABLE ONLY spellbook
    ADD CONSTRAINT fk_fqc1mrjj8qf55e69kw7rcuva0 FOREIGN KEY (createdbyuser_userid) REFERENCES appuser(userid);


--
-- Name: fk_ie1ciqvgnriqruvb7c43fjm18; Type: FK CONSTRAINT; Schema: public; Owner: springdev
--

ALTER TABLE ONLY spellbookcard
    ADD CONSTRAINT fk_ie1ciqvgnriqruvb7c43fjm18 FOREIGN KEY (spellbook) REFERENCES spellbook(spellbookid);


--
-- Name: fk_rgisujbv68qqwhmgkewuvhory; Type: FK CONSTRAINT; Schema: public; Owner: springdev
--

ALTER TABLE ONLY player
    ADD CONSTRAINT fk_rgisujbv68qqwhmgkewuvhory FOREIGN KEY (mageid) REFERENCES card(cardid);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

