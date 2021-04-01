jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DDbService } from '../service/d-db.service';
import { IDDb, DDb } from '../d-db.model';

import { DDbUpdateComponent } from './d-db-update.component';

describe('Component Tests', () => {
  describe('DDb Management Update Component', () => {
    let comp: DDbUpdateComponent;
    let fixture: ComponentFixture<DDbUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dDbService: DDbService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DDbUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DDbUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DDbUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dDbService = TestBed.inject(DDbService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dDb: IDDb = { id: 456 };

        activatedRoute.data = of({ dDb });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dDb));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dDb = { id: 123 };
        spyOn(dDbService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dDb });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dDb }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dDbService.update).toHaveBeenCalledWith(dDb);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dDb = new DDb();
        spyOn(dDbService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dDb });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dDb }));
        saveSubject.complete();

        // THEN
        expect(dDbService.create).toHaveBeenCalledWith(dDb);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dDb = { id: 123 };
        spyOn(dDbService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dDb });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dDbService.update).toHaveBeenCalledWith(dDb);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
