jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FeetypeService } from '../service/feetype.service';
import { IFeetype, Feetype } from '../feetype.model';

import { FeetypeUpdateComponent } from './feetype-update.component';

describe('Component Tests', () => {
  describe('Feetype Management Update Component', () => {
    let comp: FeetypeUpdateComponent;
    let fixture: ComponentFixture<FeetypeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let feetypeService: FeetypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FeetypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FeetypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeetypeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      feetypeService = TestBed.inject(FeetypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const feetype: IFeetype = { id: 456 };

        activatedRoute.data = of({ feetype });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(feetype));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const feetype = { id: 123 };
        spyOn(feetypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ feetype });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: feetype }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(feetypeService.update).toHaveBeenCalledWith(feetype);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const feetype = new Feetype();
        spyOn(feetypeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ feetype });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: feetype }));
        saveSubject.complete();

        // THEN
        expect(feetypeService.create).toHaveBeenCalledWith(feetype);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const feetype = { id: 123 };
        spyOn(feetypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ feetype });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(feetypeService.update).toHaveBeenCalledWith(feetype);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
