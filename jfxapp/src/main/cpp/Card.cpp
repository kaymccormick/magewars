#include "Card.h"

Card::Card()
{

}
std::string Card::GetCardName(){

	return CardName;
}


void Card::SetCardName(std::string newVal){

	CardName = newVal;
}


char Card::GetArmor(){

	return Armor;
}


char Card::GetCastCost(){

	return CastCost;
}


char Card::GetChanneling(){

	return Channeling;
}


char Card::GetLife(){

	return Life;
}


char Card::GetMaxRange(){

	return MaxRange;
}


char Card::GetMinRange(){

	return MinRange;
}


void Card::SetArmor(char newVal){

	Armor = newVal;
}


void Card::SetCastCost(char newVal){

	CastCost = newVal;
}


void Card::SetChanneling(char newVal){

	Channeling = newVal;
}


void Card::SetLife(char newVal){

	Life = newVal;
}


void Card::SetMaxRange(char newVal){

	MaxRange = newVal;
}


void Card::SetMinRange(char newVal){

	MinRange = newVal;
}