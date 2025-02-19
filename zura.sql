PGDMP  )    8                 }            postgres    17.2    17.2 a    ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            @           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            A           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            B           1262    5    postgres    DATABASE     {   CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Arabic_Jordan.1252';
    DROP DATABASE postgres;
                     postgres    false            C           0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                        postgres    false    4930            D           0    0    DATABASE postgres    ACL     ,   GRANT CONNECT ON DATABASE postgres TO alaa;
                        postgres    false    4930            E           0    0    SCHEMA public    ACL     $   GRANT ALL ON SCHEMA public TO alaa;
                        pg_database_owner    false    5            �            1259    17835    customer    TABLE     �   CREATE TABLE public.customer (
    ssn integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    phone_number character varying(15) NOT NULL
);
    DROP TABLE public.customer;
       public         heap r       postgres    false            �            1259    17838    customer_ssn_seq    SEQUENCE     �   CREATE SEQUENCE public.customer_ssn_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.customer_ssn_seq;
       public               postgres    false    218            F           0    0    customer_ssn_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.customer_ssn_seq OWNED BY public.customer.ssn;
          public               postgres    false    219            �            1259    17839    dishes    TABLE     �   CREATE TABLE public.dishes (
    name character varying(50) NOT NULL,
    preparation_time integer NOT NULL,
    description text NOT NULL,
    price numeric(10,2) NOT NULL,
    name_menu character varying(50) NOT NULL
);
    DROP TABLE public.dishes;
       public         heap r       postgres    false            �            1259    17844    employee    TABLE     _  CREATE TABLE public.employee (
    ssn integer NOT NULL,
    last_name character varying(50) NOT NULL,
    first_name character varying(50) NOT NULL,
    phone_number character varying(15) NOT NULL,
    email character varying(100) NOT NULL,
    address text NOT NULL,
    job_type character varying(50) NOT NULL,
    salary numeric(10,2) NOT NULL
);
    DROP TABLE public.employee;
       public         heap r       postgres    false            �            1259    17849    employee_ssn_seq    SEQUENCE     �   CREATE SEQUENCE public.employee_ssn_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.employee_ssn_seq;
       public               postgres    false    221            G           0    0    employee_ssn_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.employee_ssn_seq OWNED BY public.employee.ssn;
          public               postgres    false    222            �            1259    17850    invoices    TABLE     �   CREATE TABLE public.invoices (
    bills_id integer NOT NULL,
    number integer NOT NULL,
    calculation numeric(10,2) NOT NULL,
    status character varying(50) NOT NULL,
    order_id integer NOT NULL
);
    DROP TABLE public.invoices;
       public         heap r       postgres    false            �            1259    17853    invoices_bills_id_seq    SEQUENCE     �   CREATE SEQUENCE public.invoices_bills_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.invoices_bills_id_seq;
       public               postgres    false    223            H           0    0    invoices_bills_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.invoices_bills_id_seq OWNED BY public.invoices.bills_id;
          public               postgres    false    224            �            1259    17854    manager    TABLE     �   CREATE TABLE public.manager (
    ssn integer NOT NULL,
    last_name character varying(50) NOT NULL,
    first_name character varying(50) NOT NULL,
    phone_number character varying(15) NOT NULL,
    email character varying(100) NOT NULL
);
    DROP TABLE public.manager;
       public         heap r       postgres    false            �            1259    17857    manager_ssn_seq    SEQUENCE     �   CREATE SEQUENCE public.manager_ssn_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.manager_ssn_seq;
       public               postgres    false    225            I           0    0    manager_ssn_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.manager_ssn_seq OWNED BY public.manager.ssn;
          public               postgres    false    226            �            1259    18180    managerorders    TABLE     _   CREATE TABLE public.managerorders (
    order_id integer NOT NULL,
    ssn integer NOT NULL
);
 !   DROP TABLE public.managerorders;
       public         heap r       postgres    false            �            1259    17858    menu    TABLE     F   CREATE TABLE public.menu (
    name character varying(50) NOT NULL
);
    DROP TABLE public.menu;
       public         heap r       postgres    false            �            1259    18026    order_dishes    TABLE     i   CREATE TABLE public.order_dishes (
    order_id integer NOT NULL,
    name character varying NOT NULL
);
     DROP TABLE public.order_dishes;
       public         heap r       postgres    false            �            1259    17861    orders    TABLE     #  CREATE TABLE public.orders (
    order_id integer NOT NULL,
    type character varying(50) NOT NULL,
    pay_type character varying(50) NOT NULL,
    preparation_time integer NOT NULL,
    location character varying(100) NOT NULL,
    ssn integer NOT NULL,
    date character varying(20)
);
    DROP TABLE public.orders;
       public         heap r       postgres    false            �            1259    16502    orders_id_seq    SEQUENCE     �   CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.orders_id_seq;
       public               postgres    false            �            1259    17864    orders_order_id_seq    SEQUENCE     �   CREATE SEQUENCE public.orders_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.orders_order_id_seq;
       public               postgres    false    228            J           0    0    orders_order_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.orders_order_id_seq OWNED BY public.orders.order_id;
          public               postgres    false    229            �            1259    18122    reservations_reservation_id_seq    SEQUENCE     �   CREATE SEQUENCE public.reservations_reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.reservations_reservation_id_seq;
       public               postgres    false            �            1259    17869    reservation    TABLE     Q  CREATE TABLE public.reservation (
    reservation_id integer DEFAULT nextval('public.reservations_reservation_id_seq'::regclass) NOT NULL,
    start_time text NOT NULL,
    end_time text NOT NULL,
    date text NOT NULL,
    number_of_people integer NOT NULL,
    phone_number character varying(15) NOT NULL,
    ssn integer NOT NULL
);
    DROP TABLE public.reservation;
       public         heap r       postgres    false    237            �            1259    17872    reservation_customer    TABLE     l   CREATE TABLE public.reservation_customer (
    ssn integer NOT NULL,
    reservation_id integer NOT NULL
);
 (   DROP TABLE public.reservation_customer;
       public         heap r       postgres    false            �            1259    17875    reservation_reservation_id_seq    SEQUENCE     �   CREATE SEQUENCE public.reservation_reservation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.reservation_reservation_id_seq;
       public               postgres    false    230            K           0    0    reservation_reservation_id_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.reservation_reservation_id_seq OWNED BY public.reservation.reservation_id;
          public               postgres    false    232            �            1259    17876    serves    TABLE     ^   CREATE TABLE public.serves (
    tables_id integer NOT NULL,
    order_id integer NOT NULL
);
    DROP TABLE public.serves;
       public         heap r       postgres    false            �            1259    18134    table_reservations    TABLE     p   CREATE TABLE public.table_reservations (
    tables_id integer NOT NULL,
    reservation_id integer NOT NULL
);
 &   DROP TABLE public.table_reservations;
       public         heap r       postgres    false            �            1259    17883    tables    TABLE     �   CREATE TABLE public.tables (
    tables_id integer NOT NULL,
    capacity integer NOT NULL,
    status character varying(50) NOT NULL
);
    DROP TABLE public.tables;
       public         heap r       postgres    false            �            1259    17886    tables_tables_id_seq    SEQUENCE     �   CREATE SEQUENCE public.tables_tables_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.tables_tables_id_seq;
       public               postgres    false    234            L           0    0    tables_tables_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.tables_tables_id_seq OWNED BY public.tables.tables_id;
          public               postgres    false    235            �            1259    18155    waiter    TABLE       CREATE TABLE public.waiter (
    ssn integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    phone_number character varying(15),
    email character varying(100),
    address text,
    salary numeric(10,2) NOT NULL
);
    DROP TABLE public.waiter;
       public         heap r       postgres    false            a           2604    17887    customer ssn    DEFAULT     l   ALTER TABLE ONLY public.customer ALTER COLUMN ssn SET DEFAULT nextval('public.customer_ssn_seq'::regclass);
 ;   ALTER TABLE public.customer ALTER COLUMN ssn DROP DEFAULT;
       public               postgres    false    219    218            b           2604    17888    employee ssn    DEFAULT     l   ALTER TABLE ONLY public.employee ALTER COLUMN ssn SET DEFAULT nextval('public.employee_ssn_seq'::regclass);
 ;   ALTER TABLE public.employee ALTER COLUMN ssn DROP DEFAULT;
       public               postgres    false    222    221            c           2604    17889    invoices bills_id    DEFAULT     v   ALTER TABLE ONLY public.invoices ALTER COLUMN bills_id SET DEFAULT nextval('public.invoices_bills_id_seq'::regclass);
 @   ALTER TABLE public.invoices ALTER COLUMN bills_id DROP DEFAULT;
       public               postgres    false    224    223            d           2604    17890    manager ssn    DEFAULT     j   ALTER TABLE ONLY public.manager ALTER COLUMN ssn SET DEFAULT nextval('public.manager_ssn_seq'::regclass);
 :   ALTER TABLE public.manager ALTER COLUMN ssn DROP DEFAULT;
       public               postgres    false    226    225            e           2604    18070    orders order_id    DEFAULT     r   ALTER TABLE ONLY public.orders ALTER COLUMN order_id SET DEFAULT nextval('public.orders_order_id_seq'::regclass);
 >   ALTER TABLE public.orders ALTER COLUMN order_id DROP DEFAULT;
       public               postgres    false    229    228            g           2604    17895    tables tables_id    DEFAULT     t   ALTER TABLE ONLY public.tables ALTER COLUMN tables_id SET DEFAULT nextval('public.tables_tables_id_seq'::regclass);
 ?   ALTER TABLE public.tables ALTER COLUMN tables_id DROP DEFAULT;
       public               postgres    false    235    234            &          0    17835    customer 
   TABLE DATA           L   COPY public.customer (ssn, first_name, last_name, phone_number) FROM stdin;
    public               postgres    false    218   Wt       (          0    17839    dishes 
   TABLE DATA           W   COPY public.dishes (name, preparation_time, description, price, name_menu) FROM stdin;
    public               postgres    false    220   �t       )          0    17844    employee 
   TABLE DATA           n   COPY public.employee (ssn, last_name, first_name, phone_number, email, address, job_type, salary) FROM stdin;
    public               postgres    false    221   �v       +          0    17850    invoices 
   TABLE DATA           S   COPY public.invoices (bills_id, number, calculation, status, order_id) FROM stdin;
    public               postgres    false    223   >x       -          0    17854    manager 
   TABLE DATA           R   COPY public.manager (ssn, last_name, first_name, phone_number, email) FROM stdin;
    public               postgres    false    225   �x       <          0    18180    managerorders 
   TABLE DATA           6   COPY public.managerorders (order_id, ssn) FROM stdin;
    public               postgres    false    240   :y       /          0    17858    menu 
   TABLE DATA           $   COPY public.menu (name) FROM stdin;
    public               postgres    false    227   ]y       8          0    18026    order_dishes 
   TABLE DATA           6   COPY public.order_dishes (order_id, name) FROM stdin;
    public               postgres    false    236   �y       0          0    17861    orders 
   TABLE DATA           a   COPY public.orders (order_id, type, pay_type, preparation_time, location, ssn, date) FROM stdin;
    public               postgres    false    228   +z       2          0    17869    reservation 
   TABLE DATA           v   COPY public.reservation (reservation_id, start_time, end_time, date, number_of_people, phone_number, ssn) FROM stdin;
    public               postgres    false    230   �z       3          0    17872    reservation_customer 
   TABLE DATA           C   COPY public.reservation_customer (ssn, reservation_id) FROM stdin;
    public               postgres    false    231   F{       5          0    17876    serves 
   TABLE DATA           5   COPY public.serves (tables_id, order_id) FROM stdin;
    public               postgres    false    233   s{       :          0    18134    table_reservations 
   TABLE DATA           G   COPY public.table_reservations (tables_id, reservation_id) FROM stdin;
    public               postgres    false    238   �{       6          0    17883    tables 
   TABLE DATA           =   COPY public.tables (tables_id, capacity, status) FROM stdin;
    public               postgres    false    234   �{       ;          0    18155    waiter 
   TABLE DATA           b   COPY public.waiter (ssn, first_name, last_name, phone_number, email, address, salary) FROM stdin;
    public               postgres    false    239   |       M           0    0    customer_ssn_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.customer_ssn_seq', 5, true);
          public               postgres    false    219            N           0    0    employee_ssn_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.employee_ssn_seq', 7, true);
          public               postgres    false    222            O           0    0    invoices_bills_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.invoices_bills_id_seq', 5, true);
          public               postgres    false    224            P           0    0    manager_ssn_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.manager_ssn_seq', 5, true);
          public               postgres    false    226            Q           0    0    orders_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.orders_id_seq', 10, true);
          public               postgres    false    217            R           0    0    orders_order_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.orders_order_id_seq', 14, true);
          public               postgres    false    229            S           0    0    reservation_reservation_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.reservation_reservation_id_seq', 5, true);
          public               postgres    false    232            T           0    0    reservations_reservation_id_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.reservations_reservation_id_seq', 11, true);
          public               postgres    false    237            U           0    0    tables_tables_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.tables_tables_id_seq', 5, true);
          public               postgres    false    235            i           2606    17899    customer customer_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (ssn);
 @   ALTER TABLE ONLY public.customer DROP CONSTRAINT customer_pkey;
       public                 postgres    false    218            k           2606    17901    dishes dishes_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.dishes
    ADD CONSTRAINT dishes_pkey PRIMARY KEY (name);
 <   ALTER TABLE ONLY public.dishes DROP CONSTRAINT dishes_pkey;
       public                 postgres    false    220            m           2606    17903    employee employee_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (ssn);
 @   ALTER TABLE ONLY public.employee DROP CONSTRAINT employee_pkey;
       public                 postgres    false    221            o           2606    17905    invoices invoices_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.invoices
    ADD CONSTRAINT invoices_pkey PRIMARY KEY (bills_id);
 @   ALTER TABLE ONLY public.invoices DROP CONSTRAINT invoices_pkey;
       public                 postgres    false    223            q           2606    17907    manager manager_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.manager
    ADD CONSTRAINT manager_pkey PRIMARY KEY (ssn);
 >   ALTER TABLE ONLY public.manager DROP CONSTRAINT manager_pkey;
       public                 postgres    false    225            �           2606    18184     managerorders managerorders_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public.managerorders
    ADD CONSTRAINT managerorders_pkey PRIMARY KEY (order_id, ssn);
 J   ALTER TABLE ONLY public.managerorders DROP CONSTRAINT managerorders_pkey;
       public                 postgres    false    240    240            s           2606    17909    menu menu_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.menu
    ADD CONSTRAINT menu_pkey PRIMARY KEY (name);
 8   ALTER TABLE ONLY public.menu DROP CONSTRAINT menu_pkey;
       public                 postgres    false    227                       2606    18032    order_dishes order_dishes_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.order_dishes
    ADD CONSTRAINT order_dishes_pkey PRIMARY KEY (order_id, name);
 H   ALTER TABLE ONLY public.order_dishes DROP CONSTRAINT order_dishes_pkey;
       public                 postgres    false    236    236            u           2606    17911    orders orders_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (order_id);
 <   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_pkey;
       public                 postgres    false    228            y           2606    17915 .   reservation_customer reservation_customer_pkey 
   CONSTRAINT     }   ALTER TABLE ONLY public.reservation_customer
    ADD CONSTRAINT reservation_customer_pkey PRIMARY KEY (ssn, reservation_id);
 X   ALTER TABLE ONLY public.reservation_customer DROP CONSTRAINT reservation_customer_pkey;
       public                 postgres    false    231    231            w           2606    17917    reservation reservation_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (reservation_id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public                 postgres    false    230            {           2606    17919    serves serves_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.serves
    ADD CONSTRAINT serves_pkey PRIMARY KEY (tables_id, order_id);
 <   ALTER TABLE ONLY public.serves DROP CONSTRAINT serves_pkey;
       public                 postgres    false    233    233            �           2606    18138 *   table_reservations table_reservations_pkey 
   CONSTRAINT        ALTER TABLE ONLY public.table_reservations
    ADD CONSTRAINT table_reservations_pkey PRIMARY KEY (tables_id, reservation_id);
 T   ALTER TABLE ONLY public.table_reservations DROP CONSTRAINT table_reservations_pkey;
       public                 postgres    false    238    238            }           2606    17923    tables tables_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.tables
    ADD CONSTRAINT tables_pkey PRIMARY KEY (tables_id);
 <   ALTER TABLE ONLY public.tables DROP CONSTRAINT tables_pkey;
       public                 postgres    false    234            �           2606    18163    waiter waiter_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.waiter
    ADD CONSTRAINT waiter_pkey PRIMARY KEY (ssn);
 <   ALTER TABLE ONLY public.waiter DROP CONSTRAINT waiter_pkey;
       public                 postgres    false    239            �           2606    17934    dishes dishes_name_menu_fkey    FK CONSTRAINT     ~   ALTER TABLE ONLY public.dishes
    ADD CONSTRAINT dishes_name_menu_fkey FOREIGN KEY (name_menu) REFERENCES public.menu(name);
 F   ALTER TABLE ONLY public.dishes DROP CONSTRAINT dishes_name_menu_fkey;
       public               postgres    false    4723    220    227            �           2606    18038    order_dishes fk_dish    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_dishes
    ADD CONSTRAINT fk_dish FOREIGN KEY (name) REFERENCES public.dishes(name) ON DELETE CASCADE;
 >   ALTER TABLE ONLY public.order_dishes DROP CONSTRAINT fk_dish;
       public               postgres    false    220    236    4715            �           2606    18033    order_dishes fk_order    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_dishes
    ADD CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES public.orders(order_id) ON DELETE CASCADE;
 ?   ALTER TABLE ONLY public.order_dishes DROP CONSTRAINT fk_order;
       public               postgres    false    236    228    4725            �           2606    18055    invoices invoices_order_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.invoices
    ADD CONSTRAINT invoices_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(order_id) ON UPDATE CASCADE;
 I   ALTER TABLE ONLY public.invoices DROP CONSTRAINT invoices_order_id_fkey;
       public               postgres    false    223    4725    228            �           2606    18185 )   managerorders managerorders_order_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.managerorders
    ADD CONSTRAINT managerorders_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(order_id) ON DELETE CASCADE;
 S   ALTER TABLE ONLY public.managerorders DROP CONSTRAINT managerorders_order_id_fkey;
       public               postgres    false    240    4725    228            �           2606    18190 $   managerorders managerorders_ssn_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.managerorders
    ADD CONSTRAINT managerorders_ssn_fkey FOREIGN KEY (ssn) REFERENCES public.waiter(ssn) ON DELETE CASCADE;
 N   ALTER TABLE ONLY public.managerorders DROP CONSTRAINT managerorders_ssn_fkey;
       public               postgres    false    240    4739    239            �           2606    18065 '   order_dishes order_dishes_order_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_dishes
    ADD CONSTRAINT order_dishes_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(order_id) ON UPDATE CASCADE ON DELETE CASCADE;
 Q   ALTER TABLE ONLY public.order_dishes DROP CONSTRAINT order_dishes_order_id_fkey;
       public               postgres    false    236    228    4725            �           2606    17944    orders orders_ssn_fkey    FK CONSTRAINT     u   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_ssn_fkey FOREIGN KEY (ssn) REFERENCES public.customer(ssn);
 @   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_ssn_fkey;
       public               postgres    false    218    4713    228            �           2606    18102 =   reservation_customer reservation_customer_reservation_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservation_customer
    ADD CONSTRAINT reservation_customer_reservation_id_fkey FOREIGN KEY (reservation_id) REFERENCES public.reservation(reservation_id) ON DELETE CASCADE;
 g   ALTER TABLE ONLY public.reservation_customer DROP CONSTRAINT reservation_customer_reservation_id_fkey;
       public               postgres    false    230    231    4727            �           2606    17959 2   reservation_customer reservation_customer_ssn_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservation_customer
    ADD CONSTRAINT reservation_customer_ssn_fkey FOREIGN KEY (ssn) REFERENCES public.customer(ssn);
 \   ALTER TABLE ONLY public.reservation_customer DROP CONSTRAINT reservation_customer_ssn_fkey;
       public               postgres    false    218    231    4713            �           2606    18060    serves serves_order_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.serves
    ADD CONSTRAINT serves_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(order_id) ON UPDATE CASCADE ON DELETE CASCADE;
 E   ALTER TABLE ONLY public.serves DROP CONSTRAINT serves_order_id_fkey;
       public               postgres    false    228    4725    233            �           2606    17974    serves serves_tables_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.serves
    ADD CONSTRAINT serves_tables_id_fkey FOREIGN KEY (tables_id) REFERENCES public.tables(tables_id);
 F   ALTER TABLE ONLY public.serves DROP CONSTRAINT serves_tables_id_fkey;
       public               postgres    false    234    4733    233            �           2606    18144 9   table_reservations table_reservations_reservation_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.table_reservations
    ADD CONSTRAINT table_reservations_reservation_id_fkey FOREIGN KEY (reservation_id) REFERENCES public.reservation(reservation_id) ON DELETE CASCADE;
 c   ALTER TABLE ONLY public.table_reservations DROP CONSTRAINT table_reservations_reservation_id_fkey;
       public               postgres    false    238    4727    230            �           2606    18139 4   table_reservations table_reservations_tables_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.table_reservations
    ADD CONSTRAINT table_reservations_tables_id_fkey FOREIGN KEY (tables_id) REFERENCES public.tables(tables_id) ON DELETE CASCADE;
 ^   ALTER TABLE ONLY public.table_reservations DROP CONSTRAINT table_reservations_tables_id_fkey;
       public               postgres    false    234    4733    238            &   s   x�5̻�@E��܏1��PRi��Z����g:�$Z��mХ�s��2���;k���D7�S�׿�(w�cƍ��q�T�����R8�(4�M+��1gS��ڦ�<O"��!      (     x�eS�r�0<�_��G��4�ƙ��6��1H�$V��a���I9v�`w���#�U��P�Z�ฉ!���u�萃j����9�(�e�^��=ۡe˂="��J���=T]�g&�(��	��
)���^�*�,�)T�PfrF�K�����$���lU�g����M���ӊF`��3�[�@�{��/����S:h)vȵ����/ƠG�n��Q; gH^���클S�!�W���F{����
g� ���c�Q��
�����$4q��#4��)�b6w�P�E>_3i	�d��TAh[�>n��j`˸���I�+<�'�Z}�bD�J�iK��X�-�+����@~\����cD�����U�sbH24��>e�&���!/By��p)VY$;�Sc�g�AA�`�=�?��i�N�@HAr������L��&` қ��ށ
>��K�~=��薂�x[K
��/�������'���m��s�����"��'���l6��G[�      )   =  x�U��j�0E���!ɏ$�:��PB�	t���Vc=����w���{��;W�^�N ���(�z��)*\G�A�������-��4h=�l�f���'TB.D��	�=q@��zUWe�`$��7s��X���s�7W�74�)� ��=�8�	S���@�����^v9k,���j�U�Z�Zh���X���st�wɶ�����Џa�i�3�W)m�=E��zj����$���/��^	Ŷ6�����OMۆ�?A������w�u�)TU	�X0�r���mM���q
�f�����;ϲ��5��      +   ?   x�3�4�45�30�H�L�4�2�4�4Cs�p!D 5/%3/�ӄ˔Ӕ�� �ʔ+F��� C��      -   �   x�U�=� E�ˏ!����Vc\tuy*	/mJL��Bۅ�����0Z�h�~�PJ�Uݴ]�,)�'�#�X-_�%&
�<���C黶��R�lH�e^�;9����a�"�����dN#�p1d���l(��.?۞n�0}C��Sf8�aMD��d�C
!�.wP�      <      x�3�4�2�=... �      /   ,   x��MM�)�r,(H-ɬJ-*�r)���.�
.OM-)����� �*�      8   �   x�U�K
�@D�ӧ�	��x��CD�n
�!M&�2���v6W��x�]-��Ұ�i�hA[wL7E�ɫ�h���$��}�T���\<x�+�h�>2vVk��ʣ�_nɗ����2>��sCD?�F0�      0   �   x�]�;�0�z|
.�(�,�%-%�";�9�ܞȖ(��y���܃��A��[3�L;2�ae��e�-��(�0L�&Ƙń1\�${0�$���,p��5�?[KM���E��thD����8�/�?�k���V)�62O      2   l   x�M��Ac��3�w�Z��n`;T¶��Z>ԇMP��G�am���Cy45�p�I`lÕ����s!��\��Umݪ���*�o}�¼�8I�/� Z.      3      x�3�4�2�4�2�4�2�4����� �      5      x�3�4�2�4�2�4����� �      :   "   x�3�4�2�4�2�4�2�4�2�44����� ,"<      6   A   x�3�4��ON.-�LM�2�4CpL8MSNCϜӂӱ,13'1)'����3�4F(����� b�Z      ;   R   x�3��H,.N��N,J�44��073516�,��2�r���9�z����&��
�e�y��:
�U���z\1z\\\ �dA     